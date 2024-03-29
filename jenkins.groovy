pipeline {
    environment {
        registry = "rinatalibaev/insurance"
        registryCredential = 'dockerhub'

    }
    agent any
    stages {
        stage('Cloning Git') {
            steps {
                git 'https://github.com/rinatalibaev/insurance.git'
            }
        }
        stage('Building image') {
            steps{
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy Image') {
            steps{
                script {
                    docker.withRegistry( 'https://index.docker.io/v1/', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Remove Unused docker image') {
            steps{
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
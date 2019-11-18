$(document).ready(function () {
    var body = $('body');

    // Привязываем кнопку РАСЧЕТА ПРЕМИИ
    body.on('click', '#calculatePremium', function(event){
        event.preventDefault();
        return calculatePremium();
    });

    // Привызываем кнопку СОЗДАНИЯ ДОГОВОРА
    body.on('click', '#createContractButton', function(event){
        window.location = "/contract/createContract";
    });

    // Привызываем кнопку ОТКРЫТИЯ ДОГОВОРА
    body.on('click', '#openContractButton', function(event){
        window.location = "/contract/getContractById?contractId=" + localStorage.getItem('selectedContract');
    });

    // Привязываем кнопку ОТКРЫТИЯ СПИСКА ДОГОВОРОВ
    body.on('click', '#contractsOpenButton', function(event){
        window.location = "/contract/getContracts";
    });

    // Привязываем кнопку СОХРАНЕНИЯ НОВОГО КЛИЕНТА
    body.on('click', '#saveClient', function(event){
        let clientData = {};
        clientData.surname = $('#newClientSurname').val();
        clientData.firstname = $('#newClientName').val();
        clientData.patronymic = $('#newClientPatronymic').val();
        clientData.birthday = $('#newClientBirthday').val();
        saveClient(clientData);
    });

    // Привязываем кнопку ИЗМЕНЕНИЯ КЛИЕНТА
    body.on('click', '#editSaveClient', function(event){
        let clientData = {};
        clientData.id = localStorage.getItem('currentClientId');
        clientData.surname = $('#editClientSurname').val();
        clientData.firstname = $('#editClientName').val();
        clientData.patronymic = $('#editClientPatronymic').val();
        clientData.birthday = $('#editClientBirthday').val();
        clientData.passportSeries = parseInt($('#editPassportSeries').val(), 10);
        clientData.passportNumber = parseInt($('#editPassportNumber').val(), 10);
        saveClient(clientData);
    });

    // Дифференцируем клик и двойной клик по времени
    // Author:  Jacek Becela
    // Source:  http://gist.github.com/399624
    // License: MIT
    jQuery.fn.single_double_click = function(single_click_callback, double_click_callback, timeout) {
        return this.each(function(){
            var clicks = 0, self = this;
            jQuery(this).click(function(event){
                clicks++;
                if (clicks == 1) {
                    setTimeout(function(){
                        if(clicks == 1) {
                            single_click_callback.call(self, event);
                        } else {
                            double_click_callback.call(self, event);
                        }
                        clicks = 0;
                    }, timeout || 300);
                }
            });
        });
    };

    // Создаем ТАБЛИЦУ ДОГОВОРОВ
    if ($.fn.dataTable.isDataTable( '#contractsTable' )) {
        contractsTable = $('#contractsTable').DataTable();
        contractsTable.ajax.reload();
    } else {
        contractsTable = $('#contractsTable').DataTable({
            language: {
                url: "Russian.json"
            },
            "cursor": "pointer",
            columns: [
                {"data": "contractNumber"},
                {"data": "conclusionDate"},
                {"data": "clientFio"},
                {"data": "premiumAmount"},
                {"data": "startValidity"}
            ],
            ajax: {
                url: 'http://' + window.location.host + '/contract/getContracts',
                dataType: 'json',
                contentType: 'application/json',
                type: 'POST',
                // указываем, что придет массив, а не объект
                dataSrc: '',
            },
            'createdRow': function (row, data, dataIndex, cells) {
                $(cells)[0].innerHTML = data.contractNumber;
                $(cells)[1].innerHTML = new Date(data.conclusionDate).toLocaleDateString();
                $(cells)[2].innerHTML = data.clientFio;
                $(cells)[3].innerHTML = data.premiumAmount;
                $(cells)[4].innerHTML = "C " + new Date(data.startValidity).toLocaleDateString() + " по " + new Date(data.endValidity).toLocaleDateString();
                $(row).single_double_click(function() {
                    localStorage.setItem('selectedContract', data.id);
                    if ( $(this).hasClass('selected') ) {
                        $(this).removeClass('selected');
                    }
                    else {
                        $('#contractsTable tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                }, function() {
                    window.location = "/contract/getContractById?contractId=" + data.id;
                });
            }
        });
    }

    // Привязываем кнопку ВЫБОРА КЛИЕНТОВ
    body.on('click', '#chooseClient', function(event){
        if ($.fn.dataTable.isDataTable( '#clientTable' )) {
            clientTable = $('#clientTable').DataTable();
            clientTable.ajax.reload();
        }
    });

    // Привязываем кнопку ПОИСКА КЛИЕНТОВ (лупа) и создаем ТАБЛИЦУ КЛИЕНТОВ
    body.on('click', '#search-client-button', function () {
        var clientTable;
        if ($.fn.dataTable.isDataTable( '#clientTable' )) {
            clientTable = $('#clientTable').DataTable();
            clientTable.ajax.reload();
        } else {
            clientTable = $('#clientTable').DataTable({
                language: {
                    "url": "/Russian.json"
                },
                columns: [
                    {"data": "surname"},
                    {"data": "birthday"},
                    {"data": "passportSeries"},
                    {"data": "id"},
                ],
                "columnDefs": [
                    {
                        "targets": [ 3 ],
                        "visible": false
                    }
                ],
                ajax: {
                    url: 'http://' + window.location.host + '/client/searchClients',
                    dataType: 'json',
                    contentType: 'application/json',
                    type: 'POST',
                    // указываем, что придет массив, а не объект
                    dataSrc: '',
                    "data": function (d) {
                        d.surname = $('#clientSurname').val();
                        d.firstname = $('#clientName').val();
                        d.patronymic = $('#clientPatronymic').val();
                        return JSON.stringify(d);
                    }
                },
                'createdRow': function (row, data, dataIndex, cells) {
                    debugger;
                    let passportSeries = data.passportSeries === 0 ? "" : data.passportSeries;
                    let passportNumber = data.passportNumber === 0 ? "" : data.passportNumber;
                    $(cells)[0].innerHTML = data.surname + ' ' + data.firstname + ' ' + data.patronymic;
                    $(cells)[1].innerHTML = new Date(data.birthday).toLocaleDateString();
                    $(cells)[2].innerHTML =  passportSeries + " " + passportNumber;
                    $(row).on('click', function() {
                        $('#clientSelectModal').modal('hide');
                        localStorage.setItem('currentClientId', data.id);
                        enterClientInfoToContract(data);
                        let clientFio = $('#clientFio').val();
                        $('button[id$=editClient]')[0].disabled = clientFio === "";
                    })
                }
            });
        }
    });

    // Открываем МОДАЛЬНОЕ ОКНО РЕДАКТИРОВАНИЯ КЛИЕНТА
    body.on('click', '#editClient', function () {
        let fio = $('#clientFio').val();
        let fioArray = fio.split(" ");
        let surname = fioArray[0];
        let name = fioArray[1];
        let patronymic = fioArray[2];
        let birthday = $('#clientBirthday').val();
        let passportSeries = $('#passportSeries').val() == null ? "" : $('#passportSeries').val();
        let passportNumber = $('#passportNumber').val() == null ? "" : $('#passportNumber').val();
        $('#editClientModal #editClientSurname').val(surname);
        $('#editClientModal #editClientName').val(name);
        $('#editClientModal #editClientPatronymic').val(patronymic);
        $('#editClientModal #editClientBirthday').val(birthday);
        $('#editClientModal #editPassportSeries').val(passportSeries);
        $('#editClientModal #editPassportNumber').val(passportNumber);
    });

    // Сохраняем ДОГОВОР
    body.on('click', '#saveContractButton', function(){
        var contractData = {};
        if ($('#contractNumber').val().length !== 6) {
            alert('Номер договора должен состоять из 6 цифр');
            return;
        }
        contractData.id = $('#contractId').text();
        contractData.insuranceAmount = $('#insuranceAmount').val();
        contractData.realtyType = $('#realtyCoefficient option:selected').text();
        contractData.buildingYear = $('#buildingYear').val();
        contractData.realtyArea = $('#realtyArea').val();
        contractData.startValidity = $('#startValidity').val();
        contractData.endValidity = $('#endValidity').val();
        contractData.calculateDate = $('#calculateDate').val();
        contractData.premiumAmount = $('#premiumAmount').val();
        contractData.contractNumber = $('#contractNumber').val();
        contractData.conclusionDate = $('#conclusionDate').val();
        contractData.clientFio = $('#clientFio').val();
        contractData.clientBirthday = $('#clientBirthday').val();
        contractData.passportSeries = $('#passportSeries').val();
        contractData.passportNumber = $('#passportNumber').val();
        contractData.realtyState = $('#realtyState').val();
        contractData.realtyIndex = $('#realtyIndex').val();
        contractData.realtyRegion = $('#realtyRegion').val();
        contractData.realtyDistrict = $('#realtyDistrict').val();
        contractData.realtyLocality = $('#realtyLocality').val();
        contractData.realtyStreet = $('#realtyStreet').val();
        contractData.realtyHouse = $('#realtyHouse').val();
        contractData.realtyHousing = $('#realtyHousing').val();
        contractData.realtyBuilding = $('#realtyBuilding').val();
        contractData.realtyApartment = $('#realtyApartment').val();
        contractData.contractCommentText = $('#contractCommentText').val();

        $.ajax({
            type : "POST",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/contract/createContract",
            data : JSON.stringify(contractData),
            success : function(response) {
                if (response === undefined || response === null || response === "") {
                    alert('Договор не сохранен. Проверьте все данные. Номер договора должен быть уникальным');
                    return;
                }
                alert('Договор успешно сохранен');
                window.location = "/contract/getContracts";
            },
            error: function(response) {
                alert('Не удалось сохранить договор. Проверьте все поля и попробуйте еще раз');
            },
            complete: function() {
            }
        }).done(function() {

        });
    });

    // Проверяем поле "ФИО" и включаем/выключаем кнопку "ИЗМЕНИТЬ"
    let clientFio = $('#clientFio').val();
    $('button[id$=editClient]')[0].disabled = clientFio === "";
});

// Сохраняем/обновляем клиента
function saveClient (clientData) {
    if ((clientData.id == null && (clientData.surname === "" || clientData.firstname === "" || clientData.birthday === "")) ||
        (clientData.id != null && (clientData.surname === "" || clientData.firstname === "" || clientData.birthday === "" || clientData.passportSeries === "" || clientData.passportNumber === ""))) {
        alert('Вы ввели не все обязательные данные');
    } else if (clientData.id != null && (typeof clientData.passportSeries !== 'number' || typeof clientData.passportNumber !== 'number' || clientData.passportSeries.toString().length !== 4 || clientData.passportNumber.toString().length!== 6)) {
        alert('Вы ввели неверные паспортные данные');
    } else {
        $.ajax({
            type : "POST",
            contentType: "application/json; charset=utf-8",
            url : window.location.origin + "/client/saveClient",
            // dataType : 'json',
            data : JSON.stringify(clientData),
            success : function(response) {
                localStorage.setItem('currentClientId', response.id);
                $('#clientFio').val(response.surname + " " + response.firstname + " " + response.patronymic);
                $('#clientBirthday').val(response.birthday);
                if (response.passportSeries !== 0) {
                    $('.policyHolder #passportSeries').val(response.passportSeries);
                }
                if (response.passportNumber !== 0) {
                    $('.policyHolder #passportNumber').val(response.passportNumber);
                }
                let clientFio = $('#clientFio').val();
                $('button[id$=editClient]')[0].disabled = clientFio === "";
            },
            error: function(response) {
                alert('Ошибка при сохранении клиента');
            },
            complete: function() {
            }
        }).done(function() {
        });
    }
}

// После выбора клиента в таблице клиентов заполняем поля договора, касающиеся клиента
function enterClientInfoToContract (clientData) {
    $('#clientFio').val(clientData.surname + ' ' + clientData.firstname + ' ' + clientData.patronymic);
    $('#clientBirthday').val(clientData.birthday);
    if (clientData.passportSeries != null && clientData.passportSeries !== 0) {
        $('#passportSeries').val(clientData.passportSeries.toString());
    } else {
        $('#passportSeries').val("");
    }
    if (clientData.passportNumber != null && clientData.passportNumber !== 0) {
        $('#passportNumber').val(clientData.passportNumber.toString());
    } else {
        $('#passportNumber').val("");
    }
}

// Вычисляем ПРЕМИЮ
function calculatePremium() {
    var premiumCalculateData = {
        "realtyArea" : $('#realtyArea').val(),
        "buildingYear" : $('#buildingYear').val(),
        "insuranceAmount" : $('#insuranceAmount').val(),
        "realtyCoefficient" : $('#realtyCoefficient').val(),
        "startValidity" : $('#startValidity').val(),
        "endValidity" : $('#endValidity').val()
    };
    let fractionDigitsAfterComma = premiumCalculateData.realtyArea.toString().split(/,(.+)/)[1];
    let fractionDigitsAfterPoint = premiumCalculateData.realtyArea.toString().split(/\.(.+)/)[1];
    if (fractionDigitsAfterPoint !== undefined && fractionDigitsAfterPoint.length > 1 || fractionDigitsAfterComma !== undefined && fractionDigitsAfterComma.length > 1) {
        alert("Количество знаков после запятой в поле 'Площадь' не должна быть больше 1");
        return;
    }
    if (premiumCalculateData.endValidity === "" || premiumCalculateData.startValidity === "") {
        alert("Выбрана неверная дата");
        return;
    }
    premiumCalculateData.realtyArea = premiumCalculateData.realtyArea.toString().replace(",", ".");
    $.ajax({
        type : "POST",
        contentType: "application/json; charset=utf-8",
        url : window.location.origin + "/premium/calculate",
        dataType : 'json',
        data : JSON.stringify(premiumCalculateData),
        success : function(response) {
            $('#premiumAmount').val(response);
            let calculateDate = new Date(new Date().toISOString()).toLocaleDateString();
            let year = calculateDate.substring(6,10);
            let month = calculateDate.substring(3,5);
            let day = calculateDate.substring(0,2);
            calculateDate = year + "-" + month + "-" + day;
            $('#calculateDate').val(calculateDate);
            $('#conclusionDate').val(calculateDate);
        },
        error: function(response) {
            if (response.responseJSON.message.includes("Validation failed")) {
                alert(response.responseJSON.errors[0].defaultMessage);
            }
        },
        complete: function() {
        }
    }).done(function() {

    });
}

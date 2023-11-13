///Функционал Начальника:
///Загрузка своего отдела в календаре
///Выбор дня работника и установка затраченного времени
///Если пропуск - галочка на причину (подтверждено или нет)

///Функционал работника:

///В конце: отчеты за месяц HR'у


let startCache;
let endCache;
let accountWorkingDays = []
let currentIdAccount;
let accDocsCache = [];
let spentTimeTypeCache = []

function loadSpentTimeTypes() {

    if (spentTimeTypeCache.length === 0) {
        $.ajax({
            method: "get",
            url: "/spent-time-types",
            contentType: "application/json",
            async: false,
            success: (data) => {
                spentTimeTypeCache = data
            },
            error: () => {
                $("#spentTimeModal").hide()
                callMessagePopup("Ошибка", "Невозможно загрузить данные о типах затрат времени");
            }
        })
        return spentTimeTypeCache;
    } else {
        return spentTimeTypeCache;
    }
}

function loadCalendar() {
    $.ajax({
        type: "get",
        url: "/wlogger/calendar",
        contentType: "application/text",
        dataType: "text",
        async: false,
        success: function (data) {
            $("#timetable-placeholder").html(data);
        },
        error: function () {
            callMessagePopup("Ошибка", "Невозможно загрузить календарь")
        }
    })
}

function loadTimesheetDays(idAccount) {

    $.ajax({
        method: 'get',
        url: "/timesheet-days?idAccount=" + idAccount,
        contentType: "application/json",
        dataType: "json",
        async: false,
        success: (data) => {
            accountWorkingDays = data;
            accountWorkingDays.forEach(workingDay => {
                workingDay.date = new Date(workingDay.date);
            })
        },
        error: () => {
            callMessagePopup("Ошибка", "Невозможно сохранить данны")
        }
    })
}

function convertToEvents(documents) {
    if (documents.length === 0) {
        return;
    }
    const events = []
    documents.forEach(document => {
        events.push({
            id: document.idDocument,
            title: document.text,
            type: 'document',
            start: document.sinceDate,
            end: document.toDate
        })
    })

    return events;
}

var initializeCalendar = function (idAccount) {
    currentIdAccount = idAccount

    loadTimesheetDays(idAccount);
    accDocsCache = loadDocumentsForAccount(idAccount);
    const docsEvents = convertToEvents(accDocsCache);
    // TODO ВСЕ SPENT TIMES
    loadSpentTimes()

    $('.calendar').fullCalendar('destroy');
    $('.calendar').fullCalendar({
        fontsize: '1px',
        firstDay: 1,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek'
        },
        navLinks: false,
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        // create events
        events: docsEvents,
        timeFormat: 'H:mm',
        slotLabelFormat: 'H:mm',
        defaultTimedEventDuration: '00:30:00',
        forceEventDuration: true,
        eventBackgroundColor: 'cadetblue',
        height: screen.height - 170,
        timezone: 'Russia/Moscow',
        selectable: true,
        selectHelper: true,
        dayClick: function (data) {
            startCache = data._d;
            endCache = new Date(data._d);
            endCache.setDate(endCache.getDate() - 1);
        },
        select: function (start, end) {
            startCache = start._d;
            endCache = new Date(end._d);
            endCache.setDate(endCache.getDate() - 1);
        },
        unselect: function () {
            // startCache = undefined;
            // endCache = undefined;
            // selectedDayCache = undefined;
            // console.log('undelsect');
        },
        eventClick: function (calEvent, jsEvent, view) {
            if (calEvent.type === 'document') {
                openDoc(calEvent);
            }
        },
        dayRender: function (date, cell) {
            let dateObj = checkDateAndReturnIfPresent(date._d);
            if (dateObj) {
                console.log(dateObj.startTime);
                //Туть вставлять надо
                cell.text(dateObj.startTime.substring(0, 5) + "-" + dateObj.endTime.substring(0, 5))
                cell.css("background-color", "white").css("padding", "5px");
            } else {
                cell.css("background-color", "#F4F4F4");
            }
        },
    });

    getCalendars();
    disableEnter();
    appendButtons(idAccount);
    $('#calendar1').fullCalendar('option', 'height', $(window).height() - 250);
}

function openFillTimesheetDay() {
    if (typeof startCache === "undefined") {
        callMessagePopup("Ошибка", "Выберите день, который желаете заполнить");
        return;
    }
    const timesheetDay = findWorkingDayByDate(startCache)

    if (typeof timesheetDay === "undefined") {
        callMessagePopup("Ошибка", "Невозможно редактировать день, который не назначен в графике как рабочий");
        return;
    }

    $("#timesheet-day-placeholder").text("День: " + new Date(timesheetDay.date).toLocaleDateString('ru'));

    const spentTimeTypes = loadSpentTimeTypes();
    const $spentTimeTypeSelect = $("#spent-time-type-select");

    $spentTimeTypeSelect.html('');
    spentTimeTypes.forEach(spentTimeType => {
        const option = $("<option value='" + spentTimeType.idSpentTimeType + "'>" + spentTimeType.name + "</option>");
        $spentTimeTypeSelect.append(option);
    });

    loadSpentTimes(timesheetDay.idTimesheetDay);
    fillSpentTimeTable();

    $("#confirm-spent-time-btn").on('click', () => {
        const idSpentTimeType = $("#spent-time-type-select").val();
        let spentTime = $("#spent-time-input").val();

        if (!/^\d{1,3}(?:\.\d{1,2})?$/.test(spentTime)) {
            console.log(/^\d{1,3}(?:\.\d{1})?$/.test(spentTime));
            $("#spent-time-error-lbl").show()
            return;
        } else {
            $("#spent-time-error-lbl").hide();
        }
        spentTime = parseFloat(spentTime);

        createSpentTime(timesheetDay.idTimesheetDay, idSpentTimeType, spentTime)
    })

    console.log(timesheetDay);
    $("#spentTimeModal").modal('show');
}

function createSpentTime(idTimesheetDay, idSpentTimeType, spentTime) {

    const newSpentTime = {
        timesheetDay: {
            idTimesheetDay: idTimesheetDay
        },
        spentTimeType: {
            idSpentTimeType: idSpentTimeType
        },
        spentTime: spentTime
    }

    $.ajax({
        method: "post",
        url: "/timesheet-days/" + idTimesheetDay + "/spent-times",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: JSON.stringify(newSpentTime),
        success: (data) => {
            $("#save-error").hide();
            spentTimesCache.push(data);
            setTimeout(fillSpentTimeTable(), 100);
        },
        error: () => {
            $("#save-error").show();
        }
    })
}

let spentTimesCache;

function loadSpentTimes(idTimesheetDay) {
    $.ajax({
        method: "get",
        url: "/timesheet-days/" + idTimesheetDay + "/spent-times",
        contentType: "application/json",
        dataType: "json",
        async: false,
        success: (data) => {
            spentTimesCache = data;
        },
        error: () => {

            callMessagePopup("Ошибка", "Невозможно загрузить данные по выбранному дню")
        }
    })

    return spentTimesCache
}

function deleteSpentTimeFromCache(idSpentTime) {
    let index = -1;
    spentTimesCache.forEach(spentTime => {
        if (spentTime.idSpentTime === idSpentTime) {
            index = spentTimesCache.indexOf(spentTime);
        }
    })

    spentTimesCache.splice(index, 1);
}

function fillSpentTimeTable() {
    const tableName = "spent_time"
    const $dataTable = $("#" + tableName + "_table");

    destroyAndInitDataTable(tableName, $dataTable)

    spentTimesCache?.forEach(function (spentTime) {
        addRowToSpentTimeDataTable(spentTime, tableName)
    })
}

function addRowToSpentTimeDataTable(spentTime, tableId) {
    const tableName = tableId ? tableId : "default";

    $("#" + tableName + "_table").DataTable().row.add([
        spentTime.spentTime,
        spentTime.spentTimeType.name,
        "<div class='save-button' onclick='deleteSpentTime(" + spentTime.idSpentTime + ")'>Удалить</div>"
    ]).draw();
}

function deleteSpentTime(idSpentTime) {
    $.ajax({
        method: "delete",
        url: "/spent-times/" + idSpentTime,
        async: false,
        success: () => {
            deleteSpentTimeFromCache(idSpentTime);
            fillSpentTimeTable();
        },
        error: () => {
            $("#spentTimeModal").modal('hide');
            callMessagePopup("Ошибка", "Удаление невозможно")
        }
    })
}

function findWorkingDayByDate(date) {
    let foundWorkingDay;
    accountWorkingDays.forEach(workingDay => {
        if (workingDay.date.toLocaleDateString('ru') === date.toLocaleDateString('ru')) {
            foundWorkingDay = workingDay;
        }
    })

    return foundWorkingDay;
}

function appendButtons(idAccountEmployer) {
    const $calendar = $("#calendar1 .fc-right");

    const currentAccount = getAccountCache();
    const employerAccount = loadUserWithoutCaching(idAccountEmployer);

    if (currentAccount.role.name === "ROLE_HR") {
        let warnWrongRange = $("<label id='working-time-warn' class='working-time-start-warn'" +
            "style='display: none'>Начало рабочего дня не может быть позже конца рабочего дня!</label>");
        let warnStart = $("<label id='working-time-start-warn' class='invalid-label'" +
            "for='working-time-start'>Время начала неверное неверное!</label>");
        let warnEnd = $("<label id='working-time-end-warn' class='invalid-label'" +
            "for='working-time-end'>Время конца дня неверное!</label>");

        let warnDiv = $("<div></div>").append(warnStart).append(warnEnd).append(warnWrongRange)
        let div = $("<div data-tooltip='Задает для выбранной даты рабочее время' style='display: flex'></div>")
        let workTimeStartInput = $("<input id='working-time-start' style='width: 50px' placeholder='9:00'>");
        let workTimeEndInput = $("<input id='working-time-end' style='margin-left: 10px; width: 50px' placeholder='18:00'>");

        div.append(workTimeStartInput).append(workTimeEndInput).append(warnDiv)

        let button = $("<button id='update-working-date-btn' class='fc-button fc-state-default' type='button'" +
            "onclick='changeWorkingDays()'>" +
            "Рабочий/нерабочий</button>");
        $calendar.append(div).append(button);
    }

    if (currentAccount.role.name === "ROLE_BOSS" &&
        currentAccount.department.idDepartment === employerAccount.department.idDepartment) {
        let buttonDay = $("<button id='fill-timesheet-day-brn' class='fc-button fc-state-default' type='button'" +
            "onclick='openFillTimesheetDay()'>" +
            "Заполнить день</button>");
        $calendar.append(buttonDay);
    }

}

function changeWorkingDays() {
    if (typeof startCache === "undefined" || typeof endCache === "undefined") {
        callMessagePopup("Ошибка", "Выберите хотя бы один день")
        return;
    }

    if (validateWorkingTimeInputs("working-time", true)) {
        let startVal = $("#working-time-start").val().trim() === ''
            ? null :
            new Date("2000-09-09 " + $("#working-time-start").val().trim() + ":00").toLocaleTimeString('ru');
        let endVal = $("#working-time-end").val().trim() === ''
            ? null :
            new Date("2000-09-09 " + $("#working-time-end").val().trim() + ":00").toLocaleTimeString('ru')


        let startDate = new Date(startCache);
        let daysToDelete = []
        while (startDate <= endCache) {

            let dayToDelete = undefined;
            accountWorkingDays.forEach(workingDay => {
                if (workingDay.date.toDateString() === startDate.toDateString()) {
                    dayToDelete = workingDay;
                }
            })

            if (typeof dayToDelete != 'undefined') {
                daysToDelete.push(dayToDelete);
            } else {
                accountWorkingDays.push({
                        date: new Date(startDate),
                        startTime: startVal,
                        endTime: endVal
                    }
                );
            }

            startDate.setDate(startDate.getDate() + 1);
        }
        deleteWorkingDays(daysToDelete);
        saveWorkingDays();
        startCache = undefined;
        endCache = undefined;
        initializeCalendar(currentIdAccount)
    }
}

function deleteWorkingDays(workingDays) {
    const month = accountWorkingDays[0].date.getMonth();
    const fullYear = accountWorkingDays[0].date.getFullYear();
    let timesheet = {
        account: {
            idAccount: currentIdAccount
        },
        monthYear: month + "." + fullYear,
        timesheetDayList: workingDays
    }

    $.ajax({
        method: "delete",
        url: "/timesheet-days",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: JSON.stringify(timesheet),
    })

    workingDays.forEach(workingDay => {
        accountWorkingDays.splice(accountWorkingDays.indexOf(workingDay), 1)
    })

}

function saveWorkingDays() {
    if (accountWorkingDays.length === 0) {
        return;
    }
    const month = accountWorkingDays[0].date.getMonth();
    const fullYear = accountWorkingDays[0].date.getFullYear();
    let newTimesheet = {
        account: {
            idAccount: currentIdAccount
        },
        monthYear: month + "." + fullYear,
        timesheetDayList: accountWorkingDays
    }

    $.ajax({
        method: "post",
        url: "/timesheet-days",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(newTimesheet),
        async: false
    })
}

function checkDateAndReturnIfPresent(date) {
    let result = undefined;
    accountWorkingDays.forEach(workingDay => {
        if (date.toDateString() === workingDay.date.toDateString()) {
            result = workingDay;
        }
    })

    return result;
}


var getCalendars = function () {
    $cal = $('.calendar');
    $cal1 = $('#calendar1');
}

var newEvent = (start, end) => {

}

var openDoc = function (calEvent) {
    openDocDetails(calEvent.id)

    // console.log(calEvent)
}

/* --------------------------load date in navbar-------------------------- */

/* full calendar gives newly created given different ids in month/week view
    and day view. create/edit event in day (right) view, so correct for
    id change to update in month/week (left)
  */
var getCal1Id = function (cal2Id) {
    var num = cal2Id.replace('_fc', '') - 1;
    var id = "_fc" + num;
    return id;
}

var disableEnter = function () {
    $('body').bind("keypress", function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            return false;
        }
    });
}

function validateWorkingTimeInputs(templateFieldName, ignoreEmpty) {
    var startVal = $("#" + templateFieldName + "-start").val();
    var endVal = $("#" + templateFieldName + "-end").val();

    var regexp = /^(?:\d|[01]\d|2[0-3]):[0-5]\d$/;

    var startLbl = $("#" + templateFieldName + "-start-warn");
    var endLbl = $("#" + templateFieldName + "-end-warn");
    var totalLbl = $("#" + templateFieldName + "-warn");

    var startFlag = false;
    var endFlag = false;

    if (regexp.test(startVal)) {
        startFlag = true;
        startLbl.css("display", "none");
    } else {
        startLbl.css("display", "block");
        startFlag = false;
    }

    if (regexp.test(endVal)) {
        endLbl.css("display", "none");
        endFlag = true;
    } else {
        endLbl.css("display", "block");
        endFlag = false;
    }

    if (ignoreEmpty) {
        if (startVal.trim() === '') {
            startLbl.css("display", "none");
            startFlag = true;
            startVal = "6:00";
        }

        if (endVal.trim() === '') {
            endLbl.css("display", "none");
            endFlag = true;
            endVal = "17:00"
        }
    }

    let startNumber = parseInt(startVal.replace(':', ''));
    let endNumber = parseInt(endVal.replace(':', ''));

    if (startNumber >= endNumber) {
        totalLbl.css("display", "block");
        startFlag = false;
        endFlag = false;
    } else {
        totalLbl.css("display", "none");
    }

    return startFlag && endFlag
}
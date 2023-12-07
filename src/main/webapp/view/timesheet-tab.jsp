<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Рабочее время
</div>
<div class="tab-container">
    <div class="row">
        <div class="staff-timesheet-container col-3">
            <table id="staff_timesheet_table" class="display" style="width: 100%">
                <thead style="background-color: cadetblue; color: white">
                <tr>
                    <th>Имя Фамилия</th>
                    <th>Должность</th>
                </tr>
                </thead>
                <tbody id="staff_timesheet_table-tbody">

                </tbody>
            </table>
        </div>
        <div id="timetable-placeholder" class="col-9" style="background-color: white; position: sticky">
        </div>
    </div>
</div>

<link href="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.css"
      rel="stylesheet">
<script src="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.js"></script>
<script src='../js/fullcalendar/vendor/moment.min.js'></script>
<script src='../js/fullcalendar/events.js'></script>
<script src='../js/fullcalendar/vendor/fullcalendar.js'></script>
<script src='../js/fullcalendar/calendar.js'></script>

<script>

    $(document).ready(() => {
        loadStaff();
        setTimeout(fillStaffTimesheetTable(), 100)

        loadCalendar();
    })
</script>

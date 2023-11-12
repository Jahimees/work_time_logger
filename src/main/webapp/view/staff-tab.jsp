<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
  Персонал
</div>
<div class="tab-container">
  <div class="staff-container">
    <div class="header-button" id="create-account">Создать новый аккаунт сотрудника</div>
    <table id="staff_table" class="display" style="width: 100%">
      <thead style="background-color: cadetblue; color: white">
      <tr>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отдел</th>
        <th>Должность</th>
        <th>Телефон</th>
        <th>Открыть</th>
      </tr>
      </thead>
      <tbody id="staff_table-tbody">

      </tbody>
    </table>
  </div>
</div>

<link href="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.css"
      rel="stylesheet">
<script src="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.js"></script>

<script>

  $(document).ready(() => {
    loadStaff();
    setTimeout(fillStaffTable(), 100)
    bindStaffButtons();
  })
</script>
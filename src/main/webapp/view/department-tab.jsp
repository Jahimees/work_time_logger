<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Отделы
</div>
<div class="tab-container">
    <div class="department-container">
        <div class="header-button" id="create-department">Создать новый отдел</div>
        <table id="department_table" class="display" style="width: 100%">
            <thead style="background-color: cadetblue; color: white">
            <tr>
                <th>Название отдела</th>
                <th>Изменить</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody id="department_table-tbody">

            </tbody>
        </table>
    </div>
</div>

<script>

    $(document).ready(() => {
        loadDepartments();
        setTimeout(fillDepartmentTable(), 100)
        bindDepartmentButtons();
    })
</script>
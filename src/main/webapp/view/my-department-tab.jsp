<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Мой отдел
</div>
<div class="tab-container">
    <div class="my_department-container">
        <table id="my_department_table" class="display" style="width: 100%">
            <thead style="background-color: cadetblue; color: white">
            <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Должность</th>
                <th>Телефон</th>
            </tr>
            </thead>
            <tbody id="my_department_table-tbody">

            </tbody>
        </table>
    </div>
</div>

<script>

    $(document).ready(() => {
        console.log(getAccountCache())
        loadMyDepartmentStaff(getAccountCache().department?.idDepartment);
        setTimeout(fillMyDepartmentTable(), 100)
    })
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Должности
</div>
<div class="tab-container">
    <div class="department-container">
        <div class="header-button" id="create-position">Создать новую должность</div>
        <table id="position_table" class="display" style="width: 100%">
            <thead style="background-color: cadetblue; color: white">
            <tr>
                <th>Название должности</th>
                <th>Изменить</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody id="position_table-tbody">

            </tbody>
        </table>
    </div>
</div>

<script>

    $(document).ready(() => {
        loadPositions();
        setTimeout(fillPositionTable(), 100)
        bindPositionButtons();
    })
</script>
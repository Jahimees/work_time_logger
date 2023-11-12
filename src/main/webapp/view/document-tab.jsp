<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Документы
</div>
<div class="tab-container">
    <div class="header-button" id="create-document-btn">Создать документ</div>

    <table id="document_table" class="display" style="width: 100%">
        <thead style="background-color: cadetblue; color: white">
        <tr>
            <th>Имя документа</th>
            <th>Кто создал</th>
            <th>Дата создания</th>
            <th>C</th>
            <th>По</th>
            <th>Подтвержден</th>
        </tr>
        </thead>
        <tbody id="document_table-tbody">

        </tbody>
    </table>
</div>

<script>
    $(document).ready(() => {
        initDocumentTab(${idAccount});
        setTimeout(fillDocumentTable(), 100)
    })

</script>
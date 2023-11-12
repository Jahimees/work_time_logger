<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Title</title>

    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/tab.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="loading-icon">
    <img style="width: 150px" src="../image/load.gif">
</div>
<div class="inline-menu-box">
    <div class="left-menu-bar">
        <div class="medium-title m-10-px white-color" id="menu-name-placeholder">Тут имя чела</div>
        <div class="lower-title m-10-px white-color" id="menu-role-placeholder">Тут должность чела</div>
        <div onclick="openTab('setting', ${idAccount})" class="menu-button">Параметры</div>
        <div onclick="openTab('document', ${idAccount})" class="menu-button">Документы</div>
        <div onclick="openTab('timesheet', ${idAccount})" class="menu-button">Рабочее время</div>
        <div onclick="openTab('my-department', ${idAccount})" class="menu-button">Мой отдел</div>
        <sec:authorize access="hasRole('ROLE_HR')">
            <div onclick="openTab('staff', ${idAccount})" class="menu-button">Персонал</div>
            <div onclick="openTab('department', ${idAccount})" class="menu-button">Отделы</div>
            <div onclick="openTab('position', ${idAccount})" class="menu-button">Должности</div>
        </sec:authorize>
        <div class="menu-button"><a href="/logout">Выход</a></div>
    </div>
    <div id="tab-placeholder" class="tab-placeholder">
        ${idAccount}
    </div>
    <sec:authorize access="hasRole('ROLE_EMPLOYER')">
        Работник
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_BOSS')">
        Начальник
    </sec:authorize>
</div>
<jsp:include page="common/message_popup.jsp"/>
<jsp:include page="common/create_department_popup.jsp"/>
<jsp:include page="common/create_position_popup.jsp"/>
<jsp:include page="common/account_popup.jsp"/>
<jsp:include page="common/document_popup.jsp"/>
<jsp:include page="common/view_document_popup.jsp"/>
</body>

</html>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<link href="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.css"
      rel="stylesheet">
<script src="https://cdn.datatables.net/v/dt/dt-1.13.6/fh-3.4.0/sb-1.5.0/sp-2.2.0/datatables.min.js"></script>
<script src="../js/popup.js"></script>
<script src="../js/tab.js"></script>
<script src="../js/account.js"></script>
<script src="../js/department.js"></script>
<script src="../js/staff.js"></script>
<script src="../js/position.js"></script>
<script src="../js/document.js"></script>
<script>
    $(document).ready(() => {
        loadRoleCache();
        openTab('setting', ${idAccount})
    })
</script>

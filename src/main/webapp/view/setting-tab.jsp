<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="tab-header">
    Настройки
</div>
<div class="tab-container">
    <div class="setting-container">
        <div>Фото</div>
        <div class="setting-box">
            <div><b>Основная информация</b></div>
            <div>Имя</div>
            <input id="first-name-input" readonly>
            <div>Фамилия</div>
            <input id="last-name-input" readonly>
            <div>Отдел</div>
            <input id="department-input" readonly>
            <div>Должность</div>
            <input id="position-input" readonly>
        </div>

        <div class="setting-box">
            <div><b>Контактные данные</b></div>
            <div>Телефон</div>
            <input id="phone-input">
            <div>Адрес</div>
            <input id="address-input">
            <div>Дополнительная информация</div>
            <input id="about-input">
            <div class="save-button" id="save-contacts">Сохранить</div>
        </div>

        <div class="setting-box">
            <div><b>Доступ к аккаунту</b></div>
            <div>Имя пользователя</div>
            <input id="username-input">
            <div>Пароль</div>
            <input id="password-input" type="password">
            <div class="save-button" id="save-credentials">Сохранить</div>

        </div>
    </div>
</div>

<script>
    $(document).ready(() => {
        loadUser(${idAccount});
        fillSettingTab();
    })

</script>
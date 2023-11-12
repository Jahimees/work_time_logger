<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="accountModal" tabindex="-1" aria-labelledby="accountModal" aria-hidden="true">
    <div class="modal-dialog account-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="accountModalLabel">Создание/Изменение аккаунта</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-4">
                        <label class="invalid-label" id="error-firstname-lbl">
                            Имя должно иметь не менее 1 и не более 30 символов
                        </label>
                        <div>Имя*</div>
                        <input id="firstName-input-acc-popup">
                        <label class="invalid-label" id="error-lastname-lbl">
                            Фамилия должна иметь не менее 1 и не более 30 символов
                        </label>
                        <div>Фамилия*</div>
                        <input id="lastName-input-acc-popup">
                        <div>Адрес</div>
                        <input id="address-input-acc-popup">
                        <div>Доп. информация</div>
                        <input id="about-input-acc-popup">
                        <div>Телефон</div>
                        <input id="phone-input-acc-popup">
                    </div>

                    <div class="col-4">
                        <div>Отдел*</div>
                        <select id="department-select-acc-popup">
                        </select>
                        <div>Должность*</div>
                        <select id="position-select-acc-popup">
                        </select>
                    </div>

                    <div class="col-4">
                        <div>Роль*</div>
                        <select id="role-select-acc-popup">
                        </select>
                        <label class="invalid-label" id="error-username-lbl">
                            Имя пользователя должно иметь не менее 5 и не более 30 символов
                        </label>
                        <div>Имя пользователя*</div>
                        <input id="username-input-acc-popup">
                        <label class="invalid-label" id="error-password-lbl">
                            Пароль должен иметь не менее 5 и не более 30 символов
                        </label>
                        <div>Пароль*</div>
                        <input id="password-input-acc-popup" type="password">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirm-account-btn" class="btn btn-primary">
                    Подтвердить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

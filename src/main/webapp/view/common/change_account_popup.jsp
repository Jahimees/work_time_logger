<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="changeAccountModal" tabindex="-1" aria-labelledby="changeAccountModalLabel" aria-hidden="true">
    <div class="modal-dialog account-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeAccountModalLabel">Изменение аккаунта</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-4">
                        <label class="invalid-label" id="error-change-firstname-lbl">
                            Имя должно иметь не менее 1 и не более 30 символов
                        </label>
                        <div>Имя*</div>
                        <input id="firstName-change-popup">
                        <label class="invalid-label" id="error-change-lastname-lbl">
                            Фамилия должна иметь не менее 1 и не более 30 символов
                        </label>
                        <div>Фамилия*</div>
                        <input id="lastName-change-popup">
                        <div>Адрес</div>
                        <input id="address-change-popup">
                        <div>Доп. информация</div>
                        <input id="about-change-popup">
                        <div>Телефон</div>
                        <input id="phone-change-popup">
                    </div>

                    <div class="col-4">
                        <div>Отдел</div>
                        <select id="department-change-select-popup">
                        </select>
                        <div>Должность</div>
                        <select id="position-change-select-popup">
                        </select>
                    </div>

                    <div class="col-4">
                        <div>Роль</div>
                        <select id="role-change-select-popup">
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirm-change-account-btn" class="btn btn-primary">
                    Подтвердить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

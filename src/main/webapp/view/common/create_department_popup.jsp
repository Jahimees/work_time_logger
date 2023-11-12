<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="createDepartmentModal" tabindex="-1" aria-labelledby="createDepartmentModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createDepartmentModalLabel">Создание/Изменение отдела</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>
            <div class="modal-body">
                <label class="invalid-label" id="error-department-name-lbl" >
                    Название отдела должно содержать от 1 до 30 символов
                </label>
                <div>Название отдела</div>
                <input id="department-name-input">
            </div>
            <div class="modal-footer">
                <button id="confirm-btn" class="btn btn-primary">
                    Подтвердить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

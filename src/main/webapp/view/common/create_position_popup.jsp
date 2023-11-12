<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="createPositionModal" tabindex="-1" aria-labelledby="createPositionModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createPositionModalLabel">Создание/Изменение должности</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>
            <div class="modal-body">
                <label class="invalid-label" id="error-potition-name-lbl" >
                    Название должности должно содержать от 1 до 30 символов
                </label>
                <div>Название должности</div>
                <input id="position-name-input">
            </div>
            <div class="modal-footer">
                <button id="confirm-position-btn" class="btn btn-primary">
                    Подтвердить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="documentModal" tabindex="-1" aria-labelledby="documentModalLabel" aria-hidden="true">
    <div class="modal-dialog account-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="documentModalLabel">Документ</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div id="doc-name-invalid" class="invalid-label">Название должно содержать от 1 до 20 символов</div>
                        <div>Название документа</div>
                        <input id="document-name-input">
                        <div>Владелец документа *</div>
                        <input id="document-owner-input" readonly>
                        <div id="date-start-invalid" class="invalid-label">Дата начала не может быть позже даты конца</div>
                        <div>С</div>
                        <input id="date-start-input" type="date">
                        <div id="date-end-invalid" class="invalid-label">Дата начала не может быть позже даты конца</div>
                        <div>По</div>
                        <input id="date-end-input" type="date">
                        <div>Тип документа</div>
                        <select id="document-type-select">
                        </select>
                    </div>
                    <div class="col-md-6">
                        <div>Дата подтверждения *</div>
                        <input readonly>
                        <div>Кем подтвержден *</div>
                        <input readonly>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-md-5">Подтвержден</div>
                            <input class="col-md-5" id="confirm-checkbox" type="checkbox" value="Подтвержден" onclick="return false;">
                        </div>
                        <div style="margin-top: 20px">Загрузить файл</div>
                        <input id="document-input-file" type="file" accept="image/png,image/jpeg">
                    </div>
                </div>
                <div>* - поля заполняются автоматически при сохранении</div>
            </div>
            <div class="modal-footer">
                <button id="confirm-document-btn" class="btn btn-primary">
                    Подтвердить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

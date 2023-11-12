<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="viewDocumentModal" tabindex="-1" aria-labelledby="viewDocumentModalLabel" aria-hidden="true">
    <div class="modal-dialog account-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewDocumentModalLabel">Документ</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div>Название документа</div>
                        <input id="document-name-placeholder">
                        <div>Владелец документа</div>
                        <input id="document-owner-placeholder" readonly>
                        <div>Создан</div>
                        <input id="date-creation-placeholder" readonly>
                        <div>С</div>
                        <input id="date-start-placeholder" readonly>
                        <div>По</div>
                        <input id="date-end-placeholder" readonly>
                        <div>Тип документа</div>
                        <input id="document-type-placeholder" readonly>
                    </div>
                    <div class="col-md-6">
                        <div>Дата подтверждения</div>
                        <input id="date-confirm-placeholder" readonly>
                        <div>Кем подтвержден</div>
                        <input id="confirmator-placeholder" readonly>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-md-5">Подтвержден</div>
                            <input class="col-md-5" id="confirm-checkbox-placeholder" type="checkbox" value="Подтвержден">
                        </div>
                        <div>Фото документа</div>
                        <div><img id="document-photo-placeholder" style="width: 200px" src=""></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirm-view-document-btn" class="btn btn-primary">
                    Сохранить
                </button>
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Нет
                </button>
            </div>
        </div>
    </div>
</div>

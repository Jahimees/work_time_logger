<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="spentTimeModal" tabindex="-1" aria-labelledby="spentTimeModalLabel" aria-hidden="true">
    <div class="modal-dialog account-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="spentTimeModalLabel">Затраты времени</h5>
                <div class="close close-button" data-bs-dismiss="modal" aria-label="Закрыть">
                    <span aria-hidden="true">X</span>
                </div>
            </div>
            <div class="modal-body">
                <div style="margin-bottom: 30px">
                    <div id="save-error" class="invalid-label">Ошибка сохранения...</div>
                    <div id="timesheet-day-placeholder">День:</div>
                    <label class="invalid-label" id="spent-time-error-lbl">
                        Формат данных дробный: 7 или 4.5
                    </label>
                    <div>Затрачено времени (ч)</div>
                    <input id="spent-time-input">
                    <div>Тип времени</div>
                    <select id="spent-time-type-select"></select>
                    <button id="confirm-spent-time-btn" class="save-button">
                        Добавить
                    </button>
                </div>

                <table id="spent_time_table" class="display" style="width: 100%">
                    <thead style="background-color: cadetblue; color: white">
                    <tr>
                        <th>Затрачено времени</th>
                        <th>Тип времени</th>
                        <th>Удалить</th>
                    </tr>
                    </thead>
                    <tbody id="spent_time_table-tbody">

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button id="decline-btn" class="btn btn-secondary" data-bs-dismiss="modal">
                    Закрыть
                </button>
            </div>
        </div>
    </div>
</div>

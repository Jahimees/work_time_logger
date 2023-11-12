{
    let positionsCache;

    function loadPositions() {
        $.ajax({
            method: "get",
            url: "/positions",
            dataType: "json",
            async: false,
            success: (data) => {
                positionsCache = data;
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить список должностей")
            }
        })
    }

    function removePositionFromCache(idPosition) {
        let positionIndex;
        positionsCache.forEach(position => {
            if (idPosition === position.idPosition) {
                positionIndex = positionsCache.indexOf(position);
            }
        })
        positionsCache.splice(positionIndex, 1);
    }

    function updatePositionCache(newPosition) {
        positionsCache.forEach(position => {
            if (newPosition.idPosition === position.idPosition) {
                position.name = newPosition.name
            }
        })
    }

    function findPositionInCache(idPosition) {
        let foundPosition;
        positionsCache.forEach(position => {
            if (idPosition === position.idPosition) {
                foundPosition = position;
            }
        })

        return foundPosition
    }

    function getPositionsCache() {
        return positionsCache;
    }

    function addPositionToCache(position) {
        positionsCache.push(position);
    }

    function fillPositionTable() {
        const tableName = "position"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        positionsCache?.forEach(function (position) {
            addRowToPositionDataTable(position, tableName)
        })
    }

    function addRowToPositionDataTable(position, tableName) {

        $("#" + tableName + "_table").DataTable().row.add([
            position.name,
            "<div class='save-button' onclick='callChangePosition(" + position.idPosition + ")'>Изменить</div>",
            "<div class='save-button' onclick='callConfirmDeletePosition(" + position.idPosition + ")'>Удалить</div>"
        ]).draw();
    }

    function callChangePosition(idPosition) {
        $("#createPositionModal").modal('show');
        const position = findPositionInCache(idPosition);
        $("#position-name-input").val(position.name);

        $("#confirm-position-btn").unbind()
        $("#confirm-position-btn").on('click', () => {
            const positionName = $("#position-name-input").val();

            if (positionName.trim().length < 1 || positionName.trim() > 30) {
                $("#error-position-name-lbl").show();
                return;
            } else {
                $("#error-position-name-lbl").hide();
            }

            let positionData = {
                idPosition: idPosition,
                name: positionName
            }

            updatePosition(positionData)
        })
    }

    function updatePosition(position) {
        $.ajax({
            method: "patch",
            url: "/positions/" + position.idPosition,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(position),
            success: (updatedPosition) => {
                $("#createPositionModal").modal("hide");
                updatePositionCache(updatedPosition);
                fillPositionTable();
                callMessagePopup("Успех", "Данные успешно обновлены");
            },
            error: () => {
                callMessagePopup("Ошибка", "Данные не удалось изменить");
            }
        })
    }

    function callConfirmDeletePosition(idPosition) {
        callMessagePopup("Удаление", "Вы действительно хотите удалить отдел?")

        $("#decline-message-btn").css("display", "block");
        $("#confirm-message-btn").attr("onclick", "deletePosition(" + idPosition + ")");
    }

    function deletePosition(idPosition) {
        $.ajax({
            method: "delete",
            url: "/positions/" + idPosition,
            success: () => {
                removePositionFromCache(idPosition);
                fillPositionTable();
            },
            error: () => {
                console.log("Неизвестная ошибка удаления...")
            }
        })
    }

    function bindPositionButtons() {
        $("#create-position").on('click', () => {
            $("#createPositionModal").modal('show');
            $("#position-name-input").val('')

            $("#confirm-position-btn").unbind();
            $("#confirm-position-btn").on("click", () => {
                const positionName = $("#position-name-input").val()

                if (positionName.trim().length < 1 || positionName.trim() > 30) {
                    $("#error-position-name-lbl").show();
                    return;
                } else {
                    $("#error-position-name-lbl").hide();
                }

                const data = {
                    name: positionName
                }

                $.ajax({
                    method: "post",
                    url: "/positions",
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: "application/json",
                    success: (createdPosition) => {
                        $("#createPositionModal").modal("hide");
                        callMessagePopup("Успех", "Новая должность успешно создана!");
                        addPositionToCache(createdPosition);
                        fillPositionTable();
                    }
                })
            })

        })
    }
}

{
    let departmentsCache;

    function getDepartmentsCache() {
        return departmentsCache;
    }

    function addDepartmentToCache(department) {
        departmentsCache.push(department);
    }

    function removeDepartmentFromCache(idDepartment) {
        let departmentIndex;
        departmentsCache.forEach(department => {
            if (idDepartment === department.idDepartment) {
                departmentIndex = departmentsCache.indexOf(department);
            }
        })
        departmentsCache.splice(departmentIndex, 1);
    }

    function updateDepartmentCache(newDepartment) {
        departmentsCache.forEach(department => {
            if (newDepartment.idDepartment === department.idDepartment) {
                department.name = newDepartment.name
            }
        })
    }

    function findDepartmentInCache(idDepartment) {
        let foundDepartment;
        departmentsCache.forEach(department => {
            console.log(department.idDepartment + " " + idDepartment)
            if (idDepartment === department.idDepartment) {
                foundDepartment = department;
            }
        })

        return foundDepartment
    }

    function loadDepartments() {
        $.ajax({
            method: "get",
            url: "/departments",
            dataType: "json",
            async: false,
            success: (data) => {
                departmentsCache = data;
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные об отделах")
            }
        })
    }

    function fillDepartmentTable() {
        const tableName = "department"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        departmentsCache?.forEach(function (department) {
            addRowToDepartmentDataTable(department, tableName)
        })
    }

    function addRowToDepartmentDataTable(department, tableId) {
        const tableName = tableId ? tableId : "default";

        $("#" + tableName + "_table").DataTable().row.add([
            department.name,
            "<div class='save-button' onclick='callChangeDepartment(" + department.idDepartment + ")'>Изменить</div>",
            "<div class='save-button' onclick='callConfirmDelete(" + department.idDepartment + ")'>Удалить</div>"
        ]).draw();
    }

    function callConfirmDelete(idDepartment) {
        callMessagePopup("Удаление", "Вы действительно хотите удалить отдел?")

        $("#decline-message-btn").css("display", "block");
        $("#confirm-message-btn").attr("onclick", "deleteDepartment(" + idDepartment + ")");
    }

    function deleteDepartment(idDepartment) {
        $.ajax({
            method: "delete",
            url: "/departments/" + idDepartment,
            success: () => {
                removeDepartmentFromCache(idDepartment);
                fillDepartmentTable();
            },
            error: () => {
                console.log("Неизвестная ошибка удаления...")
            }
        })
    }

    function bindDepartmentButtons() {
        $("#create-department").on("click", () => {
            $("#createDepartmentModal").modal('show');
            $("#department-name-input").val('')

            $("#confirm-btn").unbind();
            $("#confirm-btn").on("click", () => {
                const departmentName = $("#department-name-input").val()

                if (departmentName.trim().length < 1 || departmentName.trim() > 30) {
                    $("#error-department-name-lbl").show();
                    return;
                } else {
                    $("#error-department-name-lbl").hide();
                }

                const data = {
                    name: departmentName
                }

                $.ajax({
                    method: "post",
                    url: "/departments",
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: "application/json",
                    success: (createdDepartment) => {
                        $("#createDepartmentModal").modal("hide");
                        callMessagePopup("Успех", "Новый отдел успешно создан!");
                        addDepartmentToCache(createdDepartment);
                        fillDepartmentTable()
                    }
                })
            })
        })
    }

    function callChangeDepartment(idDepartment) {
        $("#createDepartmentModal").modal('show');
        const department = findDepartmentInCache(idDepartment);
        $("#department-name-input").val(department.name);

        $("#confirm-btn").unbind()
        $("#confirm-btn").on('click', () => {
            const departmentName = $("#department-name-input").val();

            if (departmentName.trim().length < 1 || departmentName.trim() > 30) {
                $("#error-department-name-lbl").show();
                return;
            } else {
                $("#error-department-name-lbl").hide();
            }

            let departmentData = {
                idDepartment: idDepartment,
                name: departmentName
            }

            updateDepartment(departmentData)
        })
    }

    function updateDepartment(department) {
        $.ajax({
            method: "patch",
            url: "/departments/" + department.idDepartment,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(department),
            success: (updatedDepartment) => {
                $("#createDepartmentModal").modal("hide");
                updateDepartmentCache(updatedDepartment);
                fillDepartmentTable();
                callMessagePopup("Успех", "Данные успешно обновлены");
            },
            error: () => {
                $("#createDepartmentModal").modal("hide");
                callMessagePopup("Ошибка", "Данные не удалось изменить");
            }
        })
    }
}
{
    let staffCache;

    function loadStaff() {
        $.ajax({
            method: 'get',
            url: '/accounts',
            dataType: "json",
            async: false,
            success: (data) => {
                staffCache = data;
                console.log(data);
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные о сотрудниках")
            }
        })
    }

    function addToStaffCache(staff) {
        staffCache.push(staff);
    }

    function fillStaffTable() {
        const tableName = "staff"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        staffCache?.forEach(function (staff) {
            addRowToStaffDataTable(staff, tableName)
        })
    }

    function addRowToStaffDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const departmentName = staff.department?.name;
        const positionName = staff.position?.name;
        const phone = staff.employerDetails?.phone;

        $("#" + tableName + "_table").DataTable().row.add([
            firstName ? firstName : "Не заполнено",
            lastName ? lastName : "Не заполнено",
            departmentName ? departmentName : "Не назначен",
            positionName ? positionName : "Не назначен",
            phone ? phone : "Неизвестен",
            "<div class='save-button'>Удалить</div>"
        ]).draw();

    }

    function bindStaffButtons() {
        $("#create-account").on('click', () => {
            $("#accountModal").modal('show');

            fillAccountPopupSelectors()

            $("#confirm-account-btn").unbind();
            $("#confirm-account-btn").on("click", () => {
                const $firstName = $("#firstName-input-acc-popup");
                const $lastName = $("#lastName-input-acc-popup");
                const $phone = $("#phone-input-acc-popup");
                const $address = $("#address-input-acc-popup");
                const $about = $("#about-input-acc-popup")
                const $department = $("#department-select-acc-popup");
                const $position = $("#position-select-acc-popup");
                const $role = $("#role-select-acc-popup")
                const $username = $("#username-input-acc-popup")
                const $password = $("#password-input-acc-popup")

                let flag = true;

                if ($firstName.val().trim().length < 1 || $firstName.val().trim().length > 30) {
                    flag = false;
                    $("#error-firstname-lbl").show();
                } else {
                    $("#error-firstname-lbl").hide();
                }

                if ($lastName.val().trim().length < 1 || $lastName.val().trim().length > 30) {
                    flag = false;
                    $("#error-lastname-lbl").show();
                } else {
                    $("#error-lastname-lbl").hide();
                }

                if ($username.val().trim().length < 5 || $username.val().trim().length > 30) {
                    flag = false;
                    $("#error-username-lbl").show();
                } else {
                    $("#error-username-lbl").hide();
                }

                if ($password.val().trim().length < 5 || $password.val().trim().length > 30) {
                    flag = false;
                    $("#error-password-lbl").show();
                } else {
                    $("#error-password-lbl").hide();
                }

                if (!flag) {
                    return;
                }

                const newAccountData = {
                    employerDetails: {
                        firstName: $firstName.val().trim(),
                        lastName: $lastName.val().trim(),
                        phone: $phone?.val().trim(),
                        address: $address?.val().trim(),
                        about: $about?.val().trim(),
                    },
                    department: {
                        idDepartment: $department.val()
                    },
                    position: {
                        idPosition: $position.val()
                    },
                    role: {
                        idRole: $role.val()
                    },
                    username: $username.val(),
                    password: $password.val(),
                }

                $.ajax({
                    method: "post",
                    url: "/accounts",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(newAccountData),
                    success: (data) => {
                        staffCache.push(data)
                        $("#accountModal").modal('hide')
                        fillStaffTable()
                    },
                    error: () => {
                        callMessagePopup("Ошибка", "Не удалось создать пользователя")
                    }
                })
            })
        })
    }

    function fillAccountPopupSelectors() {
        loadDepartments();
        loadPositions();
        const $departmentSelect = $("#department-select-acc-popup");
        const $positionSelect = $("#position-select-acc-popup");
        const $roleSelect = $("#role-select-acc-popup");

        $positionSelect.html('')
        $departmentSelect.html('');
        $roleSelect.html('');

        const departmentCache = getDepartmentsCache();
        departmentCache.forEach(department => {
            const option = $("<option value='" + department.idDepartment + "'>" + department.name + "</option>");
            $departmentSelect.append(option);
        })

        const positionsCache = getPositionsCache();
        positionsCache.forEach(position => {
            const option = $("<option value='" + position.idPosition + "'>" + position.name + "</option>");
            $positionSelect.append(option);
        })

        const roleCache = getRoleCache();
        roleCache.forEach(role => {
            const option = $("<option value='" + role.idRole + "'>" + role.name + "</option>");
            $roleSelect.append(option);
        })
    }

    /////////////////////////////MY DEPARTMENT

    let myDepartmentStaffCache;

    function loadMyDepartmentStaff(idDepartment) {
        $.ajax({
            method: 'get',
            url: '/accounts?department=' + idDepartment,
            dataType: "json",
            async: false,
            success: (data) => {
                myDepartmentStaffCache = data;
                console.log(data);
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить данные о сотрудниках")
            }
        })
    }

    function fillMyDepartmentTable() {
        const tableName = "my_department"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        myDepartmentStaffCache?.forEach(function (staff) {
            addRowToMyDepartmentDataTable(staff, tableName)
        })
    }

    function addRowToMyDepartmentDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const positionName = staff.position?.name;
        const phone = staff.employerDetails?.phone;

        $("#" + tableName + "_table").DataTable().row.add([
            firstName ? firstName : "Не заполнено",
            lastName ? lastName : "Не заполнено",
            positionName ? positionName : "Не назначен",
            phone ? phone : "Неизвестен",
        ]).draw();
    }

    ////////////////TIMESHEET STAFF TABLE

    function fillStaffTimesheetTable() {
        const tableName = "staff_timesheet"
        const $dataTable = $("#" + tableName + "_table");

        destroyAndInitDataTable(tableName, $dataTable)

        staffCache?.forEach(function (staff) {
            addRowToStaffTimesheetDataTable(staff, tableName)
        })
    }

    function addRowToStaffTimesheetDataTable(staff, tableName) {
        const firstName = staff.employerDetails?.firstName;
        const lastName = staff.employerDetails?.lastName;
        const totalName = (firstName + " " + lastName).trim() ? (firstName + " " + lastName).trim() : "Не заполнено"
        const positionName = staff.position?.name;

        $("#" + tableName + "_table").DataTable().row.add([
            "<div style='cursor: pointer' onclick='initializeCalendar(" + staff.idAccount + ")'>" + totalName + "</div>",
            positionName ? positionName : "Не назначен",
        ]).draw();
    }
}
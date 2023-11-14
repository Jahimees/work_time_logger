{
    let accountCache

    function bindButtons() {
        $("#save-credentials").unbind()
        $("#save-credentials").on("click", () => {
            const data = {
                idAccount: accountCache.idAccount,
                username: $("#username-input").val(),
                password: $("#password-input").val()
            }

            $.ajax({
                method: "patch",
                url: "/accounts/" + accountCache.idAccount,
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json",
                success: (data) => {
                    accountCache = data;
                    callMessagePopup("Успех", "Данные успешно обновлены");
                    fillSettingTab();
                },
                error: () => {
                    callMessagePopup("Ошибка", "Что-то пошло не так. Возможно пользователь с таким именем существует")
                }
            })
        })

        $("#save-contacts").unbind();
        $("#save-contacts").on("click", () => {
            const data = {
                idEmployerDetails: accountCache.employerDetails.idEmployerDetails,
                phone: $("#phone-input").val(),
                about: $("#about-input").val(),
                address: $("#address-input").val()
            }

            $.ajax({
                method: "patch",
                url: "/employer-details/" + accountCache.employerDetails.idEmployerDetails,
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(data),
                success: (data) => {
                    accountCache.employerDetails = data;
                    callMessagePopup("Успех", "Изменения прошли успешно");
                },
                error: () => {
                    callMessagePopup("Ошибка", "Невозможно сохранить данные");
                }
            })
        })
    }

    function loadUserWithoutCaching(idAccount) {
        let response
        $.ajax({
            method: 'get',
            url: '/accounts/' + idAccount,
            contentType: 'application/json',
            async: false,
            success: (data) => {
                response = data;
            },
            error: () => {
                callMessagePopup('Ошибка', 'Не удалось загрузить данные аккаунта');
            }
        })

        return response;
    }

    function loadUser(idAccount) {
        $.ajax({
            method: 'get',
            url: '/accounts/' + idAccount,
            contentType: 'application/json',
            async: false,
            success: (data) => {
                accountCache = data;
                console.log(data);
            },
            error: () => {
                callMessagePopup('Ошибка', 'Не удалось загрузить данные аккаунта');
            }
        })
    }

    function getAccountCache() {
        return accountCache;
    }

    function fillSettingTab() {
        let role = accountCache.role.name === "ROLE_EMPLOYER" ? "работник" :
            (accountCache.role.name === "ROLE_HR" ? "кадровый работник" : "начальник отдела");
        $("#username-input").val(accountCache.username);
        $("#password-input").val('')
        $("#menu-name-placeholder").text(accountCache.username);
        $("#menu-role-placeholder").text(role);

        $("#address-input").val(accountCache.employerDetails.address);
        $("#phone-input").val(accountCache.employerDetails.phone);
        $("#first-name-input").val(accountCache.employerDetails.firstName);
        $("#last-name-input").val(accountCache.employerDetails.lastName);
        $("#department-input").val(accountCache?.department.name);
        $("#position-input").val(accountCache?.position.name);
        $("#about-input").val(accountCache?.employerDetails.about);

    }

    let roleCache;

    function loadRoleCache() {
        $.ajax({
            method: "get",
            url: "/roles",
            dataType: "json",
            async: false,
            success: (data) => {
                roleCache = data
            },
            error: () => {
                callMessagePopup("Ошибка", "Невозможно загрузить список ролей")
            }
        })
    }

    function getRoleCache() {
        return roleCache;
    }
}
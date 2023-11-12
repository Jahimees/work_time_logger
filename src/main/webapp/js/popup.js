{
    const $confirmMessageBtn = $("#confirm-message-btn");
    const $confirmEditBtn = $("#confirmEditBtn");

    function repairDefaultMessagePopup() {
        $("#decline-message-btn").css("display", "none");
        $confirmMessageBtn.attr("data-bs-dismiss", "modal");
        $confirmMessageBtn.attr("onclick", "");
    }

    function callMessagePopup(title, text) {
        repairDefaultMessagePopup();
        $("#messageModalText").text(text);
        $("#messageModalLabel").text(title);
        $("#messageModal").modal('show');
    }
}
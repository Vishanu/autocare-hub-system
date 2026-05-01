(function () {
    const todayInputs = document.querySelectorAll("[data-today]");
    const today = new Date().toISOString().split("T")[0];
    todayInputs.forEach((input) => {
        input.min = today;
    });

    const serviceSelect = document.querySelector("select[name='serviceId']");
    const pricePreview = document.getElementById("pricePreview");
    if (serviceSelect && pricePreview) {
        serviceSelect.addEventListener("change", function () {
            const selected = serviceSelect.options[serviceSelect.selectedIndex];
            const price = selected.getAttribute("data-price") || "0.00";
            pricePreview.textContent = "Rs. " + Number(price).toFixed(2);
        });
    }

    document.querySelectorAll("[data-validate]").forEach((form) => {
        form.addEventListener("submit", function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                form.reportValidity();
            }
        });
    });
})();

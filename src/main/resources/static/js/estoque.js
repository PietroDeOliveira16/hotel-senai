const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));

$(document).ready(function() {
    $(document).on('click', '#btnRecarregar', function() {
        $.ajax({
            url: '/receberEstoque',
            method: 'POST',
            success: function(response){
                $('#divEstoque').empty().append(response);
            },
            error: function(){}
        });
    });
});
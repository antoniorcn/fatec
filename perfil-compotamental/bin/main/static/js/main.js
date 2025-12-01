console.log("iniciado:");
(function ($) {
    "use strict";

    
    /*==================================================================
    [ Validate ]*/
    var fields = $('.validate-input input[validation]');

    // for (var i = 0; i < fields.length; i++) { 
    //     var campo = $(fields[i]);
    //     console.log("Nome :" + campo.attr('name') + "   Validação:" + campo.attr('validation'));
    // }


    $('.validate-form').on('submit',function(){
        var check = true;

        for (var i = 0; i < fields.length; i++) { 
            var campo = $(fields[i]);
            var nome =  campo.attr('name');
            var validacao = campo.attr('validation');
            console.log("Executando validação do campo Nome :" + nome + "   Validação:" + validacao);
            if (validacao != undefined && validacao != "") { 
                if (validacao == "not_empty") { 
                    if(campo.val().trim() == ''){
                        showValidate(campo);
                        check=false;
                    }
                } else if (validacao == "email") { 
                    if($(campo).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                        showValidate(campo);
                        check=false;
                    }
                }
            }
            
            
        }
        return check;
    });


    $('.validate-form .input1').each(function(){
        $(this).focus(function(){
           hideValidate(this);
       });
    });

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    

})(jQuery);
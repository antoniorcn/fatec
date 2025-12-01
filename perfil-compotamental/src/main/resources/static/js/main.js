console.log("iniciado:");

$('.js-tilt').tilt({
    scale: 1.1
})

//$('#submit-button').on('click', function(e) {
$('form').on('submit', function(e) {
    console.log("Evitando o Submit");
    submitAction(e);
});



$(document).ready(function() {
    $(window).keydown(function(event){
        if(event.keyCode == 13 && !$(document.activeElement).is('textarea')) {
            validacao();
            event.preventDefault();
            return false;
        }
    });
});

function validacao() {
    if (! formValidation() && camposReprovados.length > 0) {
        $('html,body').animate({'scrollTop':$(camposReprovados[0]).offset().top - 50}, 800);
        return false;
    }
    return true;
}

function submitAction(e) {
    console.log("Consolidando dados finais");
    prepareSortedItems();
    // alert("Valores preparados", "clique em Ok para submeter");
    console.log('Fazendo Validação');
    if (validacao()) {
        if (confirm('Confirma submissão do questionário ?')) {
            console.log("fazendo o submit");
        } else {
            e.preventDefault();
            e.stopPropagation();
        }
    } else {
        e.preventDefault();
        e.stopPropagation();
    }
}

/*==================================================================
[ Validate ]*/
var fields = $('.validate-input');

// for (var i = 0; i < fields.length; i++) {
//     var campo = $(fields[i]);
//     console.log("Nome :" + campo.attr('name') + "   Validação:" + campo.attr('validation'));
// }

console.log("Fields: ", fields);

function formValidation() {
    var check = true;
    camposReprovados.splice(0, camposReprovados.length);
    for (var i = 0; i < fields.length; i++) {
        var campo = $(fields[i]);
        var id = campo.attr('id');
        var validacao = campo.attr('validation');
        console.log("Executando validação do campo Id :" + id + "   Validação:" + validacao);
        if (validacao != undefined && validacao != "") {
            if (validacao == "not_empty") {
                var inputField = campo.find("input");
                if(inputField.val().trim() == ''){
                    console.log("Validação not_empty reprovada");
                    showValidate(campo);
                    camposReprovados.push(campo);
                    check=false;
                }
            } else if (validacao == "email") {
                var inputField = campo.find("input");
                if($(inputField).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                    console.log("Validação de email reprovada");
                    showValidate(campo);
                    camposReprovados.push(campo);
                    check=false;
                }
            } else if (validacao == "radio_unselected") {
                var inputFields = campo.find("input:checked");
                if(inputFields == undefined || inputFields.length == 0) {
                    console.log("Validação de radio reprovada");
                    showValidate(campo);
                    camposReprovados.push(campo);
                    check=false;
                }
            } else if (validacao == "check_unselected") {
                var inputFields = campo.find("input:checked");
                if(inputFields == undefined || inputFields.length == 0) {
                    console.log("Validação de radio reprovada");
                    showValidate(campo);
                    camposReprovados.push(campo);
                    check=false;
                }
            } else if (validacao == "check_unselected_10") {
                var inputFields = campo.find("input:checked");
                if(inputFields == undefined || inputFields.length != 10) {
                    console.log("Validação de radio reprovada");
                    showValidate(campo);
                    camposReprovados.push(campo);
                    check=false;
                }
            }
        }


    }
    console.log("Resultado da validação: ", check);
    return check;
}


$('.validate-form .input1').each(function(){
    $(this).focus(function(){
       hideValidate(this);
   });
});

function checaSeCampoEstaAprovado(nomeCampo) {
    return $(camposReprovados).map((i, v) => { return v.attr('id') }).filter((i, v) => {return v === nomeCampo}).length == 0;
}

function showValidate(input) {
    var thisAlert = $(input);

    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
}

function prepareSortedItems() {
    // const items = document.getElementById( [[${'items_' + elemento.variavel}]] );
    // const campo = document.getElementById( [[${elemento.variavel}]] );
    elementoValoresNoFim.forEach((elementoVariavel, idx) => {
        const items = $("#" + 'items_' + elementoVariavel + " li");
        const campo = $("#" + elementoVariavel);
        console.log("Items selecionados: ", items);
        console.log("Campo Hidden: ", campo);

        //const texto = "";
        //for (var i = 0 ; i < items.children.length; i++) {
        //    var li = items.children[i];
        //    texto += "|**|" + li.innerHTML;
        //}

        const textos = items.map((i, v) => {
            return $(v).html();
        });

        console.log("Texto elaborado: ", textos);
        campo.val(textos.toArray().join("|**|"));
    });
}

function onCheckChange( checkVarName ) {
    formValidation();
    const selectedItems = [];
    const items = $('[name=' + checkVarName.trim() + ']');
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            selectedItems.push(items[i].value);
        }
    }
    $('#' + checkVarName + '-check-quantity').text(selectedItems.length);
    if (checaSeCampoEstaAprovado("seleciona_itens_significativos-check")) {
        console.log("Items atualizados: ", selectedItems);
        elementoComplexo[ checkVarName ] = selectedItems;
        generateSortItems( checkVarName );
        $( "#ordenacao-mensagem" ).hide();
        $( "#items_ordena_itens_significativos").show();
    } else {
        $( "#ordenacao-mensagem" ).show();
        $( "#items_ordena_itens_significativos").hide();
    }
}

function generateSortItems( checkVarName ) {
    const items = elementoComplexo[ checkVarName ];
    const lista = $("#items_ordena_itens_significativos");
    lista.empty();
    for (var i = 0; i < items.length; i++) {
        lista.append('<li class="draggable-item">' + items[i] + '</li>');
    }
}

$('html,body').animate({'scrollTop': 0}, 800);
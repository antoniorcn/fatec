function limpa_formulario_cep() {
    // Limpa valores do formulário de cep.
    $("#txtEndereco").val("");
    $("#txtBairro").val("");
    $("#txtCidade").val("");
    $("#txtEstado").val("");
}
console.log("Localizacao de CEP carregado");
$(document).ready(function() {
    //Quando o campo cep perde o foco.
    $("#txtButtonCEP").click(function() {
        //Nova variável "cep" somente com dígitos.
        var cep = $("#txtCEP").val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            console.log("Valindando o CEP:  " + cep + "  Resutado: " + validacep.test(cep));
            if(validacep.test(cep)) {

                //Preenche os campos com "..." enquanto consulta webservice.
                $("#txtEndereco").val("...");
                $("#txtBairro").val("...");
                $("#txtCidade").val("...");
        

                //Consulta o webservice viacep.com.br/
                var cepUrl = "https://viacep.com.br/ws/"+ cep +"/json/?callback=?";
                console.log("CEP sendo pesquisado:  " + cepUrl);
                $.getJSON(cepUrl, function(dados) {
                    console.log("Callback acionado");
                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#txtEndereco").val(dados.logradouro);
                        $("#txtBairro").val(dados.bairro);
                        $("#txtCidade").val(dados.localidade);
                        $("#txtEstado").val(dados.uf);
                    } else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulario_cep();
                        alert("CEP não encontrado.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                limpa_formulario_cep();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulario_cep();
        }
    });
});

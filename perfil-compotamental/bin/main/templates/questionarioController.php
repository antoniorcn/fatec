	<?php

		function default_value($var, $defaultValue) {
			if (isset($_REQUEST[$var])) { 
				return $_REQUEST[$var];
			} else {
				return $defaultValue;
			}
		}

		$contato_id = 1;

		$db = new PDO('mysql:host=localhost;dbname=fatec_cursos;charset=utf8', 'fatec', 'fRcgOYqNefSNv5qQruLL');

		$nome = default_value('txtNome', "");
		$email = default_value('txtEmail', "");
		$telefone = default_value('txtTelefone', "");
		$telefonetipo = default_value('txtTelefoneTipo', "");
		$endereco = default_value('txtEndereco', "");
		$bairro = default_value('txtBairro', "");
		$cidade = default_value('txtCidade', "");
		$cep = default_value('txtCEP', "");
		$estado = default_value('txtEstado', "");
		$escolaridade = default_value('txtEscolaridade', "");
		$instituicao = default_value('txtInstituicao', "");
		$empresa = default_value('txtEmpresa', "");
		$areas = default_value('txtArea', []);
		$carreiraespec = default_value('txtCarreiraEspec', "");
		$tecemergespec =  default_value('txtTecEmergEspec', "");
		$automespec =  default_value('txtAutomEspec', "");
		$robotespec =  default_value('txtRobotEspec', "");
		$outrosepec =  default_value('txtOutrosEpec', "");
		$horarios =  default_value('txtHorario', []);
		$button =   default_value('txtButton', "");


		/* if (isset($areas)) {
			foreach($areas as $area) {
				echo "Area: $area <br/>";
			}
		}*/
		echo implode(",", $areas) . "<br/>";
		/*
		if (isset($horarios)) {
			foreach($horarios as $horario) {
				echo "Horario: $horario <br/>";
			}
		}	*/	
		session_start();
		if (isset($areas) && count($areas) > 0 && isset($horarios) && count($horarios) > 0) {
			$error = 0;
			if ($button == "submeter") {
				$sql = "INSERT INTO contatos (id, nome, email, telefone, tel_tipo, endereco, bairro, ";
				$sql = $sql . " cidade, estado, cep, escolaridade, ultima_instituicao, empresa_nome) ";
				$sql = $sql . " VALUES (0, :nome, :email, :tel, :tel_tipo, :endereco, :bairro, :cidade, ";
				$sql = $sql . " :estado, :cep, :escolaridade, :ultima_instituicao, :empresa_nome)";
				$stmt = $db->prepare($sql);
				$stmt->bindValue(':nome', $nome, PDO::PARAM_STR);
				$stmt->bindValue(':email', $email, PDO::PARAM_STR);
				$stmt->bindValue(':tel', $telefone, PDO::PARAM_STR);
				$stmt->bindValue(':tel_tipo', $telefonetipo, PDO::PARAM_STR);
				$stmt->bindValue(':endereco', $endereco, PDO::PARAM_STR);
				$stmt->bindValue(':bairro', $bairro, PDO::PARAM_STR);
				$stmt->bindValue(':cidade', $cidade, PDO::PARAM_STR);
				$stmt->bindValue(':estado', $estado, PDO::PARAM_STR);
				$stmt->bindValue(':cep', $cep, PDO::PARAM_STR);
				$stmt->bindValue(':escolaridade', $escolaridade, PDO::PARAM_STR);
				$stmt->bindValue(':ultima_instituicao', $instituicao, PDO::PARAM_STR);
				$stmt->bindValue(':empresa_nome', $empresa, PDO::PARAM_STR);
				$stmt->execute();
				$result = $stmt->rowCount();
				$contato_id = $db->lastInsertId();
				if ($result != 1) {
					$error = 1;
				}

				// Verifica as respostas das elementos e adiciona-as no banco de dados;
				$sql = "SELECT * FROM elementos";
				$stmt = $db->prepare($sql);
				$stmt->execute();
				$result = $stmt->rowCount();
				forEach( $stmt as $row ) {
					$key = in_array($row['valor'], $areas);
					//echo "Analisando elemento : " . $row['id'] . " - " . $row['valor'] . " Localizado: $key <br/>";
					
					if ( $key == true ) { 
						$pergunta_id = $row['id'];
						$pergunta_espec = default_value($row['espec_variable'], "");
						$sql = "INSERT INTO respostas (id, contato_id, pergunta_id, pergunta_espec) VALUES (0, :contato, :elemento, :pergunta_espec)";
						$stmt = $db->prepare($sql);
						$stmt->bindValue(':contato', $contato_id, PDO::PARAM_INT);
						$stmt->bindValue(':elemento', $pergunta_id, PDO::PARAM_INT);
						$stmt->bindValue(':pergunta_espec', $pergunta_espec, PDO::PARAM_STR);
						$stmt->execute();
						$result = $stmt->rowCount();
						if ($result != 1) {
							$error = 1;
						}
					}
				}

				foreach($horarios as $horario) {
					$sql = "INSERT INTO horarios (id, contato_id, horario) VALUES (0, :contato, :horario)";
					$stmt = $db->prepare($sql);
					$stmt->bindValue(':contato', $contato_id, PDO::PARAM_INT);
					$stmt->bindValue(':horario', $horario, PDO::PARAM_STR);
					$stmt->execute();
					$result = $stmt->rowCount();
					if ($result != 1) {
						$error = 1;
					}
				}
	
			}
			if ($result == 1) {
				$_SESSION['MENSAGEM'] = 'Questionario inserido com sucesso.';
				header('Location: ./success.php');
			} else { 
				$_SESSION['MENSAGEM'] = 'Erro ao registrar o questionário, por favor tente novamente mais tarde.';
				header('Location: ./questionario.php');
			}
		} else { 
			$_SESSION['MENSAGEM'] = 'Por favor escolha ao menos um tipo de curso e um horário em que você tenha disponibilidade';
			header('Location: ./questionario.php');
		}
		
?>
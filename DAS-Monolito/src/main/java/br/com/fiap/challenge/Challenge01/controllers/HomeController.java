package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ClinicaRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String home() {
        this.dev();
        return "home";
    }

    private void dev(){
        var endereco = new Endereco();
        endereco.setLogradouro("Logradouro");
        endereco.setBairro("Bairro");
        endereco.setCep("Cep");
        endereco.setComplemento("Complemento");
        endereco.setCidade("Cidade");
        endereco.setUf("Uf");
        endereco.setNumero(123);
        enderecoRepository.save(endereco);

        var clinica1 = new Clinica();
        clinica1.setNome("Clinica 1");
        clinica1.setCnpj("12.323.123-0000-11");
        clinica1.setTelefone("11 12196779");
        clinica1.setDataCadastro(LocalDate.now());
        clinica1.setRazaoSocial("Clinica 1 Ltda");
        clinica1.setFotoClinica("foto1");
        clinica1.setEndereco(endereco);
        clinica1.setRole(DasRoles.ADMIN);
        clinica1.setEmail("admin@email.com");
        clinica1.setSenha(passwordEncoder.encode("admin"));

        var clinica2 = new Clinica();
        clinica2.setNome("Clinica 2");
        clinica2.setCnpj("13.323.123-0000-11");
        clinica2.setTelefone("11 12193779");
        clinica2.setDataCadastro(LocalDate.now());
        clinica2.setRazaoSocial("Clinica 2 Ltda");
        clinica2.setFotoClinica("foto2");
        clinica2.setEndereco(endereco);
        clinica2.setRole(DasRoles.USER);
        clinica2.setEmail("user@email.com");
        clinica2.setSenha(passwordEncoder.encode("user"));

        repository.save(clinica1);
        repository.save(clinica2);
    }
}

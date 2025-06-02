package com.example.app_sus.service;

import com.example.app_sus.model.Usuario;
import com.example.app_sus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar para criptografar senha no salvar
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService { // Implementa UserDetailsService

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Injetar para criptografar senhas

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário '" + username + "' não encontrado."));

        // O Spring Security espera papéis no formato "ROLE_NOME_DO_PAPEL"
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name())
        );

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getSenha(),      // Senha JÁ CRIPTOGRAFADA do banco
                usuario.isAtivo(),       // Campo 'ativo' da sua entidade
                true,                    // accountNonExpired
                true,                    // credentialsNonExpired
                true,                    // accountNonLocked
                authorities
        );
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha SOMENTE SE ela não estiver já criptografada
        // (útil se este método for chamado para atualização também, ou se a senha for nova)
        // Uma lógica mais robusta verificaria se a senha foi alterada.
        // Para um novo usuário, a senha recebida estará em texto plano.
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) { // Checagem simples se já é BCrypt
             usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
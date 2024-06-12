package com.mycompany.crudswing;

import com.mycompany.crudswing.dao.UsuarioDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioGUI extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone;
    private JTable table;
    private DefaultTableModel tableModel;
    private UsuarioDAO usuarioDAO;

    public UsuarioGUI() {
        usuarioDAO = new UsuarioDAO();
        initComponents();
        loadUsuarios();
    }

    private void initComponents() {
        setTitle("CRUD com Java Swing e MySQL");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 100, 25);
        panel.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 10, 200, 25);
        panel.add(txtNome);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 45, 100, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 45, 200, 25);
        panel.add(txtEmail);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(10, 80, 100, 25);
        panel.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(120, 80, 200, 25);
        panel.add(txtTelefone);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.setBounds(10, 115, 100, 25);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUsuario();
            }
        });
        panel.add(btnAdd);

        JButton btnUpdate = new JButton("Atualizar");
        btnUpdate.setBounds(120, 115, 100, 25);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUsuario();
            }
        });
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.setBounds(230, 115, 100, 25);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUsuario();
            }
        });
        panel.add(btnDelete);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Telefone"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 150, 560, 200);
        panel.add(scrollPane);

        add(panel);
    }

    private void addUsuario() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        Usuario usuario = new Usuario(0, nome, email, telefone);
        usuarioDAO.addUsuario(usuario);
        loadUsuarios();
    }

    private void updateUsuario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            Usuario usuario = new Usuario(id, nome, email, telefone);
            usuarioDAO.updateUsuario(usuario);
            loadUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para atualizar.");
        }
    }

    private void deleteUsuario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            usuarioDAO.deleteUsuario(id);
            loadUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para deletar.");
        }
    }

    private void loadUsuarios() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UsuarioGUI().setVisible(true);
            }
        });
    }
}

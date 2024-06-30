package ec.edu.uce.ApiNasa.view;

import ec.edu.uce.ApiNasa.models.Api;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private JTable table;
    private Api api;
    private JLabel lblPhoto;

    public Window() throws HeadlessException {
        setTitle("NASA Photos Viewer");
        setBounds(100, 100, 1400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 764, 456);
        getContentPane().add(scrollPane);

        String[] cameras = {"FHAZ", "MAST", "CHEMCAM", "NAVCAM"};
        JComboBox<String> comboBoxCamera = new JComboBox<>(cameras);
        comboBoxCamera.setBounds(484, 528, 100, 22);
        getContentPane().add(comboBoxCamera);

        JLabel lblCameraName = new JLabel("Camera Name");
        lblCameraName.setBounds(484, 500, 138, 43);
        getContentPane().add(lblCameraName);

        JComboBox<Integer> comboBoxId = new JComboBox<>();
        comboBoxId.setBounds(740, 528, 100, 22);
        getContentPane().add(comboBoxId);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(740, 500, 138, 43);
        getContentPane().add(lblId);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(610, 528, 89, 23);
        btnFilter.addActionListener(e -> {
            if (api == null) {
                api = new Api();
            }
            api.getApi(table, comboBoxCamera, comboBoxId, lblCameraName);
            lblPhoto.setIcon(null); // Limpiar imagen en lblPhoto al filtrar
        });
        getContentPane().add(btnFilter);

        JButton btnSearch = new JButton("View Image");
        btnSearch.setBounds(850, 528, 100, 23);
        btnSearch.addActionListener(e -> {
            if (api != null) {
                api.getPhotoById(lblPhoto, comboBoxId);
            }
        });
        getContentPane().add(btnSearch);

        lblPhoto = new JLabel();
        lblPhoto.setBounds(784, 11, 590, 456);
        getContentPane().add(lblPhoto);
    }
}

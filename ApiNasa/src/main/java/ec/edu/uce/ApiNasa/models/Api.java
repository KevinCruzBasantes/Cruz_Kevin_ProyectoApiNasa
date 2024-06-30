package ec.edu.uce.ApiNasa.models;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Api {

    private DefaultTableModel tableModel;
    private List<JSONObject> filteredPhotos;

    public void getApi(JTable table, JComboBox<String> comboBoxCamera, JComboBox<Integer> comboBoxId, JLabel foto) {
        tableModel = new DefaultTableModel();
        String[] columnNames = {"ID", "Sol", "Camera Name", "Camera Abbreviation", "Image Source", "Earth Date", "Rover Name", "Rover Status"};
        tableModel.setColumnIdentifiers(columnNames);
        table.setModel(tableModel); // Set the table model here

        try {
            URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=R3YwMaFxAQFmV3D0ukekIGu2CHq41P66jA4lgy57");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JSONObject json = new JSONObject(response.toString());
            JSONArray photos = json.getJSONArray("photos");

            List<JSONObject> photoList = new ArrayList<>();
            for (int i = 0; i < photos.length(); i++) {
                photoList.add(photos.getJSONObject(i));
            }

            String selectedCamera = comboBoxCamera.getSelectedItem().toString();
            filteredPhotos = photoList.stream()
                    .filter(photo -> photo.getJSONObject("camera").getString("name").equals(selectedCamera))
                    .collect(Collectors.toList());

            List<Integer> ids = new ArrayList<>();
            for (JSONObject photo : filteredPhotos) {
                int id = photo.getInt("id");
                int sol = photo.getInt("sol");
                JSONObject camera = photo.getJSONObject("camera");
                String cameraName = camera.getString("full_name");
                String cameraAbbreviation = camera.getString("name");
                String imgSrc = photo.getString("img_src");
                String earthDate = photo.getString("earth_date");
                JSONObject rover = photo.getJSONObject("rover");
                String roverName = rover.getString("name");
                String roverStatus = rover.getString("status");

                tableModel.addRow(new Object[]{id, sol, cameraName, cameraAbbreviation, imgSrc, earthDate, roverName, roverStatus});
                ids.add(id);
            }

            comboBoxId.removeAllItems();
            for (Integer id : ids) {
                comboBoxId.addItem(id);
            }

            if (!filteredPhotos.isEmpty()) {
                String imgUrl = filteredPhotos.get(0).getString("img_src");
                ImageIcon icon = new ImageIcon(new URL(imgUrl));
                foto.setIcon(icon);
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR: " + e.getMessage());
        }
    }

    public void getPhotoById(JLabel foto, JComboBox<Integer> comboBoxId) {
        int selectedId = (Integer) comboBoxId.getSelectedItem();
        JSONObject selectedPhoto = filteredPhotos.stream()
                .filter(photo -> photo.getInt("id") == selectedId)
                .findFirst()
                .orElse(null);

        if (selectedPhoto != null) {
            try {
                String imgUrl = selectedPhoto.getString("img_src");
                // Replacing "http" with "https" in the URL string
                imgUrl = imgUrl.replace("http://", "https://");
                ImageIcon icon = new ImageIcon(new URL(imgUrl));
                foto.setIcon(icon);
            } catch (Exception e) {
                throw new RuntimeException("ERROR: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}

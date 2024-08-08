package gestioncomida;
/**
 *
 * @author José Ortíz
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Gestioncomida2 {
    // Modelo
    static class MenuModel {
        private Map<String, String> menuItems;
        public MenuModel() {
            menuItems = new HashMap<>();
            menuItems.put("Manzana", "Una fruta crujiente y dulce, es rica en antioxidantes y vitaminas del grupo B ");
            menuItems.put("Banana", "Una fruta amarilla y suave, es una fruta radioactiva ya que contiene potasio.");
            menuItems.put("Zanahoria", "Un vegetal naranja y crujiente, mejora la vista y es buena para las uñas y el cabello.");
            menuItems.put("Espinaca", "Un vegetal verde y nutritivo, contiene altos niveles de hierro.");
            menuItems.put("Uvas", "Frutas pequeñas y jugosas, existen mas de 10.000 especies :O ");
            menuItems.put("Tomate", "Un vegetal rojo y jugoso, esta compuesto en gran parte por agua y contiene muchos carbohidratos :)");
            menuItems.put("Pimiento", "Un vegetal colorido y crujiente, hay muchas variedades y colores, como lo son verde, rojo, amarillo, naranja e incluso negro.");
            menuItems.put("Pepino", "Un vegetal verde y refrescante, es muy saludable, ya que aporta fibra, vitamina C, privitamina A y vitamina E.");
            menuItems.put("Fresa", "Una fruta roja y dulce, ayuda para muchas enfermedades como lo son el colesterol alto, la presión arterial alta y el estrés.");
            menuItems.put("Brócoli", "Un vegetal verde y saludable, tiene carotenos, vitmaina a y c, ácido fólico, potasio y hierro, sin embargo no es muy apreciada por las personas :(");
        }
        public String[] getMenuItems() {
            return menuItems.keySet().toArray(new String[0]);
        }
        public String getDescription(String item) {
            return menuItems.getOrDefault(item, "Descripción no disponible.");
        }
        public void registerOrder(String item) {
            System.out.println("Orden registrada: " + item);
        }
    }
    // Vista
    static class MenuView {
        private JFrame frame;
        private JComboBox<String> itemComboBox;
        private JButton orderButton;
        private JTextArea descriptionArea;
        private MenuModel model;
        public MenuView() {
            frame = new JFrame("Gestión del Menú de Frutas y Verduras");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());
            // ComboBox para los ítems del menú
            itemComboBox = new JComboBox<>();
            frame.add(itemComboBox, BorderLayout.NORTH);

            descriptionArea = new JTextArea();
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            frame.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

            orderButton = new JButton("Realizar Pedido");
            frame.add(orderButton, BorderLayout.SOUTH);

            itemComboBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    String selectedItem = (String) itemComboBox.getSelectedItem();
                    if (selectedItem != null) {
                        descriptionArea.setText(model.getDescription(selectedItem));
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    descriptionArea.setText("");
                }
            });

            itemComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedItem = (String) itemComboBox.getSelectedItem();
                        if (selectedItem != null) {
                            descriptionArea.setText(model.getDescription(selectedItem));
                        }
                    }
                }
            });
        }
        public void setMenuModel(MenuModel model) {
            this.model = model;
        }
        public void setMenuItems(String[] items) {
            itemComboBox.setModel(new DefaultComboBoxModel<>(items));
        }
        public String getSelectedItem() {
            return (String) itemComboBox.getSelectedItem();
        }
        public void setDescription(String description) {
            descriptionArea.setText(description);
        }
        public void addOrderListener(ActionListener listener) {
            orderButton.addActionListener(listener);
        }
        public void setVisible(boolean visible) {
            frame.setVisible(visible);
        }
        public void showConfirmation(String message) {
            JOptionPane.showMessageDialog(frame, message, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
        public void showError(String message) {
            JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Controlador
    static class MenuController {
        private MenuModel model;
        private MenuView view;
        public MenuController(MenuModel model, MenuView view) {
            this.model = model;
            this.view = view;
            view.setMenuModel(model);

            view.setMenuItems(model.getMenuItems());

            view.addOrderListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedItem = view.getSelectedItem();
                    if (selectedItem != null) {
                        model.registerOrder(selectedItem);
                        view.showConfirmation("Pedido realizado: " + selectedItem);
                    } else {
                        view.showError("Por favor, selecciona un ítem del menú.");
                    }
                }
            });
        }
    }
    // Clase Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuModel model = new MenuModel();
            MenuView view = new MenuView();
            MenuController controller = new MenuController(model, view);
            view.setVisible(true);
        });
    }
}

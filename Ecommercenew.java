package ecommercenew;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Ecommercenew {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Top 10 Trending Products");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);

            StringBuilder output = new StringBuilder("Top 10 Trending Products:\n\n");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    Ecommercenew.class.getResourceAsStream("/data/top10_products.csv")))) {

                String headerLine = br.readLine(); // Read header
                String[] headers = headerLine.split(",");

                int nameIndex = -1;
                int priceIndex = -1;

                // Find column indexes
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].trim().equalsIgnoreCase("ProductName")) {
                        nameIndex = i;
                    }
                    if (headers[i].trim().equalsIgnoreCase("Price")) {
                        priceIndex = i;
                    }
                }

                if (nameIndex == -1 || priceIndex == -1) {
                    output.append("Error: 'ProductName' or 'Price' column not found.\n");
                } else {
                    String line;
                    int count = 1;
                    while ((line = br.readLine()) != null && count <= 10) {
                        String[] values = line.split(",", -1);
                        if (values.length > Math.max(nameIndex, priceIndex)) {
                            String productName = values[nameIndex].trim();
                            String price = values[priceIndex].trim();
                            output.append(count + ". " + productName + " - ₹" + price + "\n");
                            count++;
                        }
                    }
                }

            } catch (Exception e) {
                output.append("Error reading file: ").append(e.getMessage());
            }

            textArea.setText(output.toString());
        });
    }
}



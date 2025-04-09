package ui;

import algorithms.MergeSort;
import algorithms.QuickSort;
import models.Animal;
import search.BinarySearch;
import search.SequentialSearch;
import structures.BinarySearchTree;
import structures.DynamicArray;
import structures.LinkedList;
import utils.CSVHandler;
import utils.Timer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class AnimalGUI extends JFrame
{
    private List<Animal> importedAnimals;
    private DynamicArray<Animal> dynamicArray;
    private LinkedList<Animal> linkedList;
    private BinarySearchTree<Animal> bst;
    private JComboBox<String> dsComboBox, algoComboBox, sortByComboBox;
    private JTextField searchField;
    private JTable animalTable;
    private DefaultTableModel tableModel;
    private JTextArea algorithmOutputArea;
    private JLabel infoLabel;
    private String currentDS;

    public AnimalGUI()
    {
        setTitle("Zoo Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem importItem = new JMenuItem("Import Data");
        fileMenu.add(importItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel(new FlowLayout());
        dsComboBox = new JComboBox<>(new String[]{"Dynamic Array", "Linked List", "Binary Search Tree"});
        algoComboBox = new JComboBox<>(new String[]{"MergeSort", "QuickSort", "SequentialSearch", "BinarySearch", "General Search"});
        sortByComboBox = new JComboBox<>(new String[]{"ID", "Species", "Age", "Diet (Herbivore/Carnivore)"});
        searchField = new JTextField(10);
        JButton executeButton = new JButton("Execute Algorithm");
        topPanel.add(new JLabel("Data Structure:"));
        topPanel.add(dsComboBox);
        topPanel.add(new JLabel("Algorithm:"));
        topPanel.add(algoComboBox);
        topPanel.add(new JLabel("Sort By:"));
        topPanel.add(sortByComboBox);
        topPanel.add(new JLabel("Search Key:"));
        topPanel.add(searchField);
        topPanel.add(executeButton);
        JButton clearOutputButton = new JButton("Clear Output");
        clearOutputButton.addActionListener(e -> algorithmOutputArea.setText(""));
        topPanel.add(clearOutputButton);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Species", "Age", "Herbivore"}, 0);
        animalTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(animalTable);
        algorithmOutputArea = new JTextArea();
        algorithmOutputArea.setEditable(false);
        JScrollPane algoScrollPane = new JScrollPane(algorithmOutputArea);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, algoScrollPane);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        infoLabel = new JLabel("No data imported.");
        add(infoLabel, BorderLayout.SOUTH);

        importItem.addActionListener(e -> importData());
        executeButton.addActionListener(e -> executeAlgorithm());
        dsComboBox.addActionListener(e -> convertDataStructure());

        algoComboBox.addActionListener(e ->
        {
            String selectedAlgo = (String) algoComboBox.getSelectedItem();
            if ("General Search".equals(selectedAlgo))
            {
                JOptionPane.showMessageDialog(this,
                        "General Search uses species-based search.\nPlease enter an animal species (e.g., 'lion') in the search key.",
                        "General Search Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(AnimalGUI::new);
    }

    private void importData()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            tableModel.setRowCount(0);
            try
            {
                importedAnimals = CSVHandler.importAnimalsFromCSV(file);
                for (Animal animal : importedAnimals)
                {
                    tableModel.addRow(new Object[]{animal.getId(), animal.getName(), animal.getSpecies(), animal.getAge(), animal.isHerbivore()});
                }
                algorithmOutputArea.setText("");
                searchField.setText("");
                algoComboBox.setSelectedIndex(0);
                dsComboBox.setSelectedIndex(0);
                sortByComboBox.setSelectedIndex(0);
                algorithmOutputArea.append("Imported " + importedAnimals.size() + " animals.\n");
            } catch (IOException ex)
            {
                algorithmOutputArea.append("Error reading file.\n");
            }
            convertDataStructure();
        }
    }

    private void convertDataStructure()
    {
        String dsType = (String) dsComboBox.getSelectedItem();
        currentDS = dsType;
        if (importedAnimals == null || importedAnimals.isEmpty())
        {
            algorithmOutputArea.append("No data to convert.\n");
            return;
        }
        switch (dsType)
        {
            case "Dynamic Array":
                dynamicArray = new DynamicArray<>();
                for (Animal a : importedAnimals)
                {
                    dynamicArray.add(a);
                }
                linkedList = null;
                bst = null;
                break;
            case "Linked List":
                linkedList = new LinkedList<>();
                for (Animal a : importedAnimals)
                {
                    linkedList.add(a);
                }
                dynamicArray = null;
                bst = null;
                break;
            case "Binary Search Tree":
                bst = new BinarySearchTree<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
                for (Animal a : importedAnimals)
                {
                    bst.insert(a);
                }
                dynamicArray = null;
                linkedList = null;
                break;
        }

        if ("Binary Search Tree".equals(dsType))
        {
            algoComboBox.setEnabled(false);
            sortByComboBox.setEnabled(false);
        } else
        {
            algoComboBox.setEnabled(true);
            sortByComboBox.setEnabled(true);
        }

        infoLabel.setText("Dataset size: " + importedAnimals.size() + " | Data Structure: " + dsType);
    }

    private Animal[] getAnimalArrayFromCurrentDS()
    {
        if ("Dynamic Array".equals(currentDS) && dynamicArray != null)
        {
            int size = dynamicArray.getSize();
            Animal[] animals = new Animal[size];
            for (int i = 0; i < size; i++)
            {
                animals[i] = dynamicArray.get(i);
            }
            return animals;
        } else if ("Linked List".equals(currentDS) && linkedList != null)
        {
            Object[] temp = linkedList.toArray();
            Animal[] animals = new Animal[temp.length];
            for (int i = 0; i < temp.length; i++)
            {
                animals[i] = (Animal) temp[i];
            }
            return animals;
        }
        return new Animal[0];
    }

    private void executeAlgorithm()
    {
        if (importedAnimals == null || importedAnimals.isEmpty())
        {
            algorithmOutputArea.append("No data available. Please import a file.\n");
            return;
        }

        if ("Binary Search Tree".equals(currentDS))
        {
            List<Animal> sortedAnimals = bst.inorderTraversal();
            StringBuilder sb = new StringBuilder("BST In-Order Sorted Result:\n");
            for (Animal animal : sortedAnimals)
            {
                sb.append(animal).append("\n");
            }
            algorithmOutputArea.setText(sb.toString());
            infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: Binary Search Tree | Sorted by Name");
            return;
        }

        String dsType = currentDS;
        String algo = (String) algoComboBox.getSelectedItem();
        String searchKey = searchField.getText().trim();
        Timer timer = new Timer();
        String resultMsg = "";
        switch (algo)
        {
            case "MergeSort":
            case "QuickSort":
                resultMsg = handleSort(dsType, algo, timer);
                break;
            case "BinarySearch":
                resultMsg = handleBinarySearch(searchKey, timer);
                break;
            case "SequentialSearch":
                resultMsg = handleSequentialSearch(dsType, searchKey, timer);
                break;
            case "General Search":
                resultMsg = handleGeneralSearch(searchKey, timer);
                break;
            default:
                algorithmOutputArea.append("Algorithm not recognized.\n");
                return;
        }
        if (!resultMsg.isEmpty())
        {
            algorithmOutputArea.append(resultMsg + "\n");
        }
    }

    private String handleSort(String dsType, String algo, Timer timer)
    {
        Comparator<Animal> comparator = getComparator();
        Animal[] animalArray = getAnimalArrayFromCurrentDS();
        timer.start();
        if ("MergeSort".equals(algo))
        {
            MergeSort.sort(animalArray, comparator);
        } else
        {
            QuickSort.sort(animalArray, comparator);
        }
        double duration = timer.stop();
        if ("Linked List".equals(dsType) && linkedList != null)
        {
            linkedList.fromArray(animalArray);
        }
        StringBuilder sb = new StringBuilder(algo + " result:\n");
        for (Animal a : animalArray)
        {
            sb.append(a).append("\n");
        }
        infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: " + dsType + " | Algorithm: " + algo + " | Speed: " + duration + " ms");
        return sb.toString();
    }

    private String handleBinarySearch(String searchKey, Timer timer)
    {
        if (!"Dynamic Array".equals(currentDS))
        {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(animalTable),
                    "BinarySearch is only available for Dynamic Array.\nOther data structures do not support index-based access.",
                    "Search Not Supported",
                    JOptionPane.WARNING_MESSAGE);
            algorithmOutputArea.append("[Warning] BinarySearch is only available for Dynamic Array.\n");
            return "";
        }
        try
        {
            int targetId = Integer.parseInt(searchKey);
            Animal[] animalArray = getAnimalArrayFromCurrentDS();
            Comparator<Animal> byId = Comparator.comparingInt(Animal::getId);
            MergeSort.sort(animalArray, byId);
            timer.start();
            int index = BinarySearch.search(animalArray, new Animal(targetId, "", "", 0, false), byId);
            double duration = timer.stop();
            infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: Dynamic Array | Algorithm: BinarySearch | Speed: " + duration + " ms");
            return (index != -1) ? "BinarySearch: Found at index " + index : "BinarySearch: Not found";
        } catch (NumberFormatException e)
        {
            algorithmOutputArea.append("Enter valid numeric ID for search.\n");
            return "";
        }
    }

    private String handleSequentialSearch(String dsType, String searchKey, Timer timer)
    {
        if (searchKey.isEmpty())
        {
            algorithmOutputArea.append("Enter valid numeric ID for SequentialSearch.\n");
            return "";
        }
        try
        {
            int targetId = Integer.parseInt(searchKey);
            timer.start();
            if ("Dynamic Array".equals(dsType))
            {
                Animal[] array = getAnimalArrayFromCurrentDS();
                Comparator<Animal> byId = Comparator.comparingInt(Animal::getId);
                int index = SequentialSearch.search(array, new Animal(targetId, "", "", 0, false), byId);
                double duration = timer.stop();
                infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: Dynamic Array | Algorithm: SequentialSearch | Speed: " + duration + " ms");
                return (index != -1) ? "SequentialSearch: Found at index " + index : "SequentialSearch: Not found";
            } else if ("Linked List".equals(dsType))
            {
                Animal found = null;
                Animal[] array = getAnimalArrayFromCurrentDS();
                for (Animal a : array)
                {
                    if (a.getId() == targetId)
                    {
                        found = a;
                        break;
                    }
                }
                double duration = timer.stop();
                infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: Linked List | Algorithm: SequentialSearch | Speed: " + duration + " ms");
                return (found != null) ? "SequentialSearch: Found\n" + found : "SequentialSearch: Not found";
            } else
            {
                JOptionPane.showMessageDialog(this,
                        "SequentialSearch is not available for Binary Search Tree.\n\nBST is already structured for efficient name-based lookup.",
                        "Search Not Supported",
                        JOptionPane.WARNING_MESSAGE);
                algorithmOutputArea.append("[Warning] SequentialSearch is not available for Binary Search Tree.\nBST supports search only by name using its own logic.\n");
                return "";
            }
        } catch (NumberFormatException e)
        {
            algorithmOutputArea.append("Enter valid numeric ID for search.\n");
            return "";
        }
    }

    private String handleGeneralSearch(String searchKey, Timer timer)
    {
        if ("Binary Search Tree".equals(currentDS))
        {
            JOptionPane.showMessageDialog(this,
                    "General Search is not supported on Binary Search Tree.\nIt is optimized only for Dynamic Array or Linked List.",
                    "Not Available",
                    JOptionPane.WARNING_MESSAGE);
            algorithmOutputArea.append("[Warning] General Search is disabled for Binary Search Tree.\n");
            return "";
        }
        timer.start();
        int count = 0;
        String lowerKey = searchKey.toLowerCase();
        if (lowerKey.equals("meat") || lowerKey.equals("carnivore"))
        {
            for (Animal a : importedAnimals)
            {
                if (!a.isHerbivore()) count++;
            }
        } else
        {
            for (Animal a : importedAnimals)
            {
                if (a.getSpecies().equalsIgnoreCase(searchKey)) count++;
            }
        }
        double duration = timer.stop();
        infoLabel.setText("Dataset: " + importedAnimals.size() + " | Data Structure: " + currentDS + " | Algorithm: General Search | Speed: " + duration + " ms");
        return "General Search: Found " + count + " matching animals for '" + searchKey + "'";
    }

    private Comparator<Animal> getComparator()
    {
        String sortField = (String) sortByComboBox.getSelectedItem();
        switch (sortField)
        {
            case "Species":
                return Comparator.comparing(Animal::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "Age":
                return Comparator.comparingInt(Animal::getAge);
            case "Diet (Herbivore/Carnivore)":
                return Comparator.comparing(Animal::isHerbivore);
            case "ID":
            default:
                return Comparator.comparingInt(Animal::getId);
        }
    }
}
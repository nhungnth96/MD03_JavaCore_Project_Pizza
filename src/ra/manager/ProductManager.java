package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.controller.CategoryController;
import ra.controller.ProductController;
import ra.model.product.Category;
import ra.model.product.Product;
import ra.model.user.RoleName;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private ProductController productController;
    private CategoryController categoryController;
    private List<Product> productList;
    private List<Category> categoryList;

    public ProductManager() {
    }

    public ProductManager(ProductController productController, CategoryController categoryController) {
        this.productController = productController;
        this.categoryController = categoryController;
        this.productList = productController.getAll();
        this.categoryList = categoryController.getAll();
        while (true) {
            System.out.println("======PRODUCT MANAGER======");
            System.out.println("1. Show product");
            System.out.println("2. Search product");
            System.out.println("3. Create product");
            System.out.println("4. Edit product");
            System.out.println("5. Delete product");
            System.out.println("6. Change status");
            System.out.println("7. Fill stock");
            System.out.println("8. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    showAllProduct(productList);
                    break;
                case 2:
                    searchProduct(productList);
                    break;
                case 3:
                    createProduct();
                    break;
                case 4:
                    editProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    changeProductStatus();
                    break;
                case 7:
                    fillProductStock();
                    break;
                case 8:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 8) {
                break;
            }
        }

    }

    public static void showAllProduct(List<Product> productList) {

        if (productList.size() == 0) {
            System.err.println("\u001B[33mEmpty product list\u001B[0m");
            return;
        }
        while (true) {
            System.out.println("======Menu======");
            System.out.println("1. View All");
            System.out.println("2. View By Category");
            System.out.println("3. Back");
            System.out.println("Enter choice: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                        for (Product product : productList) {
                            System.out.println(product.displayForAdmin());
                        }
                    } else {
                        for (Product product : productList) {
                            if (product.isStatus()) {
                                System.out.println(product);
                            }
                        }
                    }
                    break;
                case 2:
                    CategoryController categoryController = new CategoryController();
                    List<Category> categories = categoryController.getAll();
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i).getName());
                    }
                    System.out.println("Enter choice: ");
                    while(true){
                        int catChoice = InputMethods.getInteger();
                        if (catChoice >= 1 && catChoice <= categories.size()) {
                            String catName = categories.get(catChoice - 1).getName();
                            for (Product product : productList) {
                                if (product.getCategory().getName().equals(catName)) {
                                    if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                                        System.out.println(product.displayForAdmin());
                                    } else {
                                        if (product.isStatus()) {
                                            System.out.println(product);
                                        }
                                    }
                                }
                            }
                            break;
                        } else {
                            System.err.println(InputMethods.ERROR_ALERT);
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 3) {
                break;
            }
        }

    }

    public static void searchProduct(List<Product> productList) {
        while (true) {
            System.out.println("======Search======");
            System.out.println("1. By name ");
            System.out.println("2. By des");
            System.out.println("3. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    searchByName(productList);
                    break;
                case 2:
                    searchByDes(productList);
                    break;
                case 3:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }

            if (choice >= 1 && choice <= 3) {
                break;
            }
        }
    }

    private static void searchByName(List<Product> productList) {
        System.out.println("Enter keyword: ");
        String name = InputMethods.getString();
        List<Product> resultByName = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(name)) {
                resultByName.add(product);
            }
        }
        if (resultByName.isEmpty()) {
            System.err.println(Alert.NOT_FOUND);
        } else {
            for (Product product : resultByName) {
                if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                    System.out.println(product.displayForAdmin());
                } else {
                    if (product.isStatus()) {
                        System.out.println(product);
                    }
                }
            }

        }
    }

    private static void searchByDes(List<Product> productList) {
        while (true) {
            System.out.println("1. Seafood ");
            System.out.println("2. Chicken ");
            System.out.println("3. Beef ");
            System.out.println("4. Pork ");
            System.out.println("5. Vegetable ");
            System.out.println("6. Back");
            System.out.println("Enter choice: ");
            byte choiceDes = InputMethods.getByte();
            String des = "";
            switch (choiceDes) {
                case 1:
                    des = "Seafood";
                    break;
                case 2:
                    des = "Chicken";
                    break;
                case 3:
                    des = "Beef";
                    break;
                case 4:
                    des = "Pork";
                    break;
                case 5:
                    des = "Vegetable";
                    break;
                case 6:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (!des.isEmpty()) {

                if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                    for (Product product : productList) {
                        if (product.getProductDes().equals(des)) {
                            System.out.println(product.displayForAdmin());
                        }
                    }
                } else {
                    for (Product product : productList) {
                        if (product.getProductDes().equals(des)) {
                            if (product.isStatus()) {
                                System.out.println(product);
                            }
                        }
                    }
                }
            }
            if (choiceDes >= 1 && choiceDes <= 6) {
                break;
            }
        }
    }

    public void createProduct() {
        System.out.println("Enter quantity want to add: ");
        int quantity = InputMethods.getInteger();
        for (int i = 1; i <= quantity; i++) {
            Product product = new Product();
            product.setProductId(productController.getNewId());
            System.out.println("Product ID: " + product.getProductId());
            product.inputData(categoryList);
            productController.save(product);
            System.out.println(Alert.SUCCESS);
        }
    }

    public void editProduct() {
        System.out.println("Enter product ID: ");
        Integer editId = InputMethods.getInteger();
        Product editProduct = productController.findById(editId);
        if (editProduct == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println(editProduct);
        editProduct.inputData(categoryList);
        productController.save(editProduct);
        System.out.println(Alert.SUCCESS);
    }

    public void deleteProduct() {
        System.out.println("Enter product ID: ");
        int deleteId = InputMethods.getInteger();
        productController.delete(deleteId);
        System.out.println(Alert.SUCCESS);
    }

    public void changeProductStatus() {
        System.out.println("Enter product ID: ");
        int changeStatusProductId = InputMethods.getInteger();
        Product changeStatusProduct = productController.findById(changeStatusProductId);
        if (changeStatusProduct == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println(changeStatusProduct);
        changeStatusProduct.setStatus(!changeStatusProduct.isStatus());
        productController.save(changeStatusProduct);
        System.out.println(Alert.SUCCESS);
    }

    public void fillProductStock() {
        System.out.println("Enter product ID: ");
        int fillProductStockId = InputMethods.getInteger();
        Product fillProductStock = productController.findById(fillProductStockId);
        if (fillProductStock == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println("Stock: " + fillProductStock.getStock());
        System.out.println("Enter quantity want to fill: ");
        int fillQuantity = InputMethods.getInteger();
        fillProductStock.setStock(fillProductStock.getStock() + fillQuantity);
        productController.save(fillProductStock);
        System.out.println(Alert.SUCCESS);
    }
}

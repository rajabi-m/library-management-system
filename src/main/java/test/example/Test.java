package test.example;

import org.example.controller.ConnectionBridge;
import org.example.controller.LibraryController;
import org.example.model.Asset;
import org.example.model.Book;
import org.example.model.User;
import org.example.service.requests.AddAssetRequest;
import org.example.service.requests.BorrowAssetRequest;
import org.example.service.requests.ReturnAssetRequest;
import org.example.service.requests.SubscribeToAssetRequest;
import org.example.service.response.Response;

import java.time.LocalDate;

public class Test {


    public static void main(String[] args) {
        // initialization
        ConnectionBridge connectionBridge = new ConnectionBridge();
        LibraryController libraryController = new LibraryController(connectionBridge);
        new Thread(libraryController).start();

        // adding test data
        Asset asset = new Book("title", "author", 2021);
        Response<String> response = connectionBridge.sendRequestAndWaitForResponse(
                new AddAssetRequest(asset)
        );
        System.out.println(response.data());

        Response<String> response00 = connectionBridge.sendRequestAndWaitForResponse(
                new BorrowAssetRequest(asset.getId(), LocalDate.now())
        );
        System.out.println(response00.data());

        Asset asset2 = new Book("title2", "author", 2021);
        Response<String> response11 = connectionBridge.sendRequestAndWaitForResponse(
                new AddAssetRequest(asset2)
        );
        System.out.println(response11.data());

        Response<String> response001 = connectionBridge.sendRequestAndWaitForResponse(
                new BorrowAssetRequest(asset2.getId(), LocalDate.now())
        );
        System.out.println(response001.data());

        // testing subscribe
        User test = new User("User 3");
        User user1 = new User("User 1");
        Response<String> response1 = connectionBridge.sendRequestAndWaitForResponse(
                new SubscribeToAssetRequest(asset.getId(), user1)
        );
        System.out.println(response1.data());

        Response<String> response13 = connectionBridge.sendRequestAndWaitForResponse(
                new SubscribeToAssetRequest(asset2.getId(), user1)
        );
        System.out.println(response13.data());

        Response<String> response2 = connectionBridge.sendRequestAndWaitForResponse(
                new SubscribeToAssetRequest(asset.getId(), new User("User 2"))
        );
        System.out.println(response2.data());

        Response<String> response3 = connectionBridge.sendRequestAndWaitForResponse(
                new ReturnAssetRequest(asset2.getId())
        );
        System.out.println(response3.data());


    }
}

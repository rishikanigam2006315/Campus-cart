package com.rishika.campuscart.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            String firebaseCreds = System.getenv("FIREBASE_CREDENTIALS");

            InputStream serviceAccount;

            if (firebaseCreds != null && !firebaseCreds.isEmpty()) {

                // Render Environment Variable
                serviceAccount =
                        new ByteArrayInputStream(firebaseCreds.getBytes());

            } else {

                // Local System File
                serviceAccount = getClass()
                        .getClassLoader()
                        .getResourceAsStream("serviceAccountKey.json");
            }

            if (serviceAccount == null) {
                System.out.println("Firebase credentials not found");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(serviceAccount)
                    )
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Initialized Successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


import java.time.LocalDate;
import java.util.Objects;

/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
/**
 * Represents a document submitted by a volunteer as part of the verification
 * process.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class Document {
    private final DocumentType documentType;
    private final LocalDate expiryDate;
    private final String imagePath;

    /**
     * Creates a new document.
     *
     * @param documentType type of document provided
     * @param expiryDate   expiry date if applicable, may be {@code null}
     * @param imagePath    path or identifier to the stored image
     */
    public Document(DocumentType documentType, LocalDate expiryDate, String imagePath) {
        this.documentType = Objects.requireNonNull(documentType, "documentType");
        this.expiryDate = expiryDate;
        this.imagePath = imagePath;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Document{" +
                "type=" + documentType +
                (expiryDate != null ? ", expiry=" + expiryDate : "") +
                (imagePath != null ? ", image='" + imagePath + '\'' : "") +
                '}';
    }
}


/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a document submitted by a volunteer as part of the verification
 * process.
 */
public class Document {
    /** Document category. */
    private final DocumentType documentType;

    /** Expiry date if applicable. */
    private final LocalDate expiryDate;

    /** Stored image location or reference. */
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

    /**
     * @return document type
     */
    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * @return expiry date or {@code null}
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * @return image reference or {@code null}
     */
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

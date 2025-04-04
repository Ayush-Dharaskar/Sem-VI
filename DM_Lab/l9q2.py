from sklearn.datasets import fetch_20newsgroups
from collections import defaultdict
from sklearn.model_selection import train_test_split
import numpy as np
# Load 20 Newsgroups Dataset
newsgroups = fetch_20newsgroups(subset='all')
X = newsgroups.data
y = newsgroups.target
classes = newsgroups.target_names


# Text Preprocessing (Tokenization and Counting)
def tokenize(text):
    return text.lower().split()


# Build Vocabulary
vocab = set()
for doc in X:
    vocab.update(tokenize(doc))
vocab = list(vocab)
vocab_size = len(vocab)


# Convert text to feature vectors
def text_to_vector(text):
    vector = [0] * vocab_size
    tokens = tokenize(text)
    for token in tokens:
        if token in vocab:
            vector[vocab.index(token)] += 1
    return vector


X_vectors = np.array([text_to_vector(doc) for doc in X])


# Multinomial Naive Bayes Classifier
class MultinomialNaiveBayes:
    def fit(self, X, y):
        self.classes = np.unique(y)
        self.class_counts = defaultdict(int)
        self.feature_counts = defaultdict(lambda: np.zeros(X.shape[1]))
        self.class_priors = {}

        for cls in self.classes:
            X_cls = X[y == cls]
            self.class_counts[cls] = len(X_cls)
            self.feature_counts[cls] = X_cls.sum(axis=0) + 1  # Laplace smoothing
            self.class_priors[cls] = self.class_counts[cls] / len(y)

    def predict(self, X):
        predictions = []
        for i in range(X.shape[0]):
            posteriors = {}
            for cls in self.classes:
                log_prior = np.log(self.class_priors[cls])
                log_likelihood = np.sum(np.log(self.feature_counts[cls] / np.sum(self.feature_counts[cls])))
                log_posterior = log_prior + log_likelihood
                posteriors[cls] = log_posterior
            predictions.append(max(posteriors, key=posteriors.get))
        return np.array(predictions)


# Split the data
X_train, X_test, y_train, y_test = train_test_split(X_vectors, y, test_size=0.3, random_state=42)

# Train the model
mnb = MultinomialNaiveBayes()
mnb.fit(X_train, y_train)

# Make predictions
y_pred = mnb.predict(X_test)

# Calculate Accuracy
accuracy = np.mean(y_pred == y_test)
print(f"Accuracy of Multinomial Naive Bayes: {accuracy * 100:.2f}%")

from sklearn.metrics import confusion_matrix

# Confusion Matrix
conf_matrix = confusion_matrix(y_test, y_pred)
print(f"Confusion Matrix:\n{conf_matrix}")

# Accuracy
accuracy = np.trace(conf_matrix) / np.sum(conf_matrix)
print(f"Accuracy: {accuracy * 100:.2f}%")

# Precision, Recall for each class
precision = np.diag(conf_matrix) / np.sum(conf_matrix, axis=0)
recall = np.diag(conf_matrix) / np.sum(conf_matrix, axis=1)

print(f"Precision: {precision}")
print(f"Recall: {recall}")

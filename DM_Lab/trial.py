import pandas as pd
from sklearn.tree import DecisionTreeClassifier, export_text
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import LabelEncoder

# Sample Dataset
data = {
    'Outlook': ['Sunny', 'Sunny', 'Overcast', 'Rain', 'Rain', 'Rain', 'Overcast', 'Sunny', 'Sunny', 'Rain', 'Sunny', 'Overcast', 'Overcast', 'Rain'],
    'Temperature': ['Hot', 'Hot', 'Hot', 'Mild', 'Cool', 'Cool', 'Cool', 'Mild', 'Cool', 'Mild', 'Mild', 'Mild', 'Hot', 'Mild'],
    'Humidity': ['High', 'High', 'High', 'High', 'Normal', 'Normal', 'Normal', 'High', 'Normal', 'Normal', 'Normal', 'High', 'Normal', 'High'],
    'Wind': ['Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Strong'],
    'PlayTennis': ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes', 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']
}

# Create DataFrame
df = pd.DataFrame(data)

# Initialize LabelEncoder
le = LabelEncoder()

# Fit LabelEncoder on all unique values across the dataset (to handle new unseen labels)
for col in df.columns:
    le.fit(df[col])
    df[col] = le.transform(df[col])  # Transform the dataset columns

# Features and Target
X = df.drop('PlayTennis', axis=1)
y = df['PlayTennis']

# Split the dataset (80% train, 20% test)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Decision Tree Classifier (ID3 Algorithm)
clf = DecisionTreeClassifier(criterion='entropy')  # Using 'entropy' for ID3
clf.fit(X_train, y_train)

# Display the Decision Tree
tree_rules = export_text(clf, feature_names=list(X.columns))
print("Decision Tree:\n")
print(tree_rules)

# Classify New Samples
new_samples = pd.DataFrame([
    {'Outlook': 'Sunny', 'Temperature': 'Cool', 'Humidity': 'Normal', 'Wind': 'Strong'},
    {'Outlook': 'Rain', 'Temperature': 'Mild', 'Humidity': 'Normal', 'Wind': 'Strong'}
])

# Encode new samples using the same LabelEncoder fitted earlier
for col in new_samples.columns:
    new_samples[col] = le.transform(new_samples[col])

# Predict using the trained classifier
predictions = clf.predict(new_samples)

# Display Predictions
for i, sample in enumerate(new_samples.itertuples(), 1):
    print(f"\nNew Sample {i}: {sample}")
    print(f"Predicted Class: {'Yes' if predictions[i-1] == 1 else 'No'}")

# Evaluate Accuracy on Test Data
y_pred = clf.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"\nAccuracy on Test Data: {accuracy * 100:.2f}%")

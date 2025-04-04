import pandas as pd
import numpy as np


# Function to calculate the entropy of a dataset
def entropy(data):
    class_counts = data['PlayTennis'].value_counts()
    probabilities = class_counts / len(data)
    return -np.sum(probabilities * np.log2(probabilities))


# Function to calculate information gain for an attribute
def information_gain(data, attribute):
    total_entropy = entropy(data)

    # Get the unique values of the attribute
    values = data[attribute].unique()

    weighted_entropy = 0
    for value in values:
        subset = data[data[attribute] == value]
        weighted_entropy += (len(subset) / len(data)) * entropy(subset)

    return total_entropy - weighted_entropy


# Function to build the decision tree recursively
def id3(data, attributes):
    # Base case: If all examples have the same classification, return that class
    if len(data['PlayTennis'].unique()) == 1:
        return data['PlayTennis'].iloc[0]

    # Base case: If there are no more attributes to split on, return the majority class
    if len(attributes) == 0:
        return data['PlayTennis'].mode()[0]

    # Choose the attribute with the highest information gain
    gains = [information_gain(data, attribute) for attribute in attributes]
    best_attribute = attributes[np.argmax(gains)]

    # Create the decision tree with the best attribute as root
    tree = {best_attribute: {}}

    # Recurse for each value of the best attribute
    for value in data[best_attribute].unique():
        subset = data[data[best_attribute] == value]
        subset_attributes = [attr for attr in attributes if attr != best_attribute]
        tree[best_attribute][value] = id3(subset, subset_attributes)

    return tree


# Function to print the tree in a human-readable format
def print_tree(tree, indent=""):
    for attribute, branches in tree.items():
        print(f"{indent}{attribute}")
        for value, subtree in branches.items():
            print(f"{indent}  {value} ->", end=" ")
            if isinstance(subtree, dict):  # If the subtree is a dictionary, it is not a leaf
                print()  # Print newline for better formatting
                print_tree(subtree, indent + "    ")
            else:
                print(subtree)


# Example dataset (play tennis dataset)
data = {
    'Outlook': ['Sunny', 'Sunny', 'Overcast', 'Rain', 'Rain', 'Rain', 'Overcast', 'Sunny', 'Sunny', 'Rain', 'Sunny',
                'Overcast', 'Overcast', 'Rain'],
    'Temperature': ['Hot', 'Hot', 'Hot', 'Mild', 'Cool', 'Cool', 'Cool', 'Mild', 'Cool', 'Mild', 'Mild', 'Mild', 'Hot',
                    'Mild'],
    'Humidity': ['High', 'High', 'High', 'High', 'Normal', 'Normal', 'Normal', 'High', 'Normal', 'Normal', 'Normal',
                 'High', 'Normal', 'High'],
    'Wind': ['Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong',
             'Weak', 'Strong'],
    'PlayTennis': ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes', 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']
}

# Convert data into a pandas DataFrame
df = pd.DataFrame(data)

# List of all attributes excluding the target variable 'PlayTennis'
attributes = ['Outlook', 'Temperature', 'Humidity', 'Wind']

# Build the decision tree
tree = id3(df, attributes)

# Display the decision tree in a readable format
print("Decision Tree:")
print(tree)
print_tree(tree)

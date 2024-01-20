# COMP6651 - Algorithm Design Techniques

## Introduction


This project aimed to create a Plagiarism Detection Tool leveraging the Rabin-Karp Algorithm for normal text documents and Cosine Similarity for source code comparison. The tool's initial task was to identify the type of input file, distinguishing between source code (e.g., Java, C++, C, or Python) and text files. This differentiation was achieved by detecting specific keywords unique to each programming language, such as "import java" for Java or "#include" for C/C++. Following file identification, the tool provided four distinct scenarios for plagiarism detection, each employing tailored preprocessing methods.

For Rabin-Karp-based plagiarism detection (in the case of both files being text files), the tool implemented preprocessing steps like removing multiple spaces and punctuation. Conversely, when using Cosine Similarity for source code comparison, the preprocessing involved removing multiple spaces and single-line comments. Additionally, the project incorporated Trie Data Structure to efficiently store and search for previously identified patterns in the file data. Overall, the Plagiarism Detection Tool utilized a combination of algorithms and data structures, enhancing its accuracy and efficiency in identifying similarities between different types of files.

The core algorithms employed in the project included the Rabin-Karp Algorithm, which efficiently searched for patterns in text files, and Cosine Similarity, a powerful method for comparing source code. These algorithms were complemented by the Trie Data Structure, contributing to constant-time pattern searches and further optimizing the tool's overall performance. The comprehensive approach ensured that the Plagiarism Detection Tool provided reliable and efficient results, with a total time complexity of O(m*n), where m is the string size of file 1 and n is the string size of file 2.

## Requirements 
* [JDK](https://www.oracle.com/java/technologies/downloads/) version 8 or higher;

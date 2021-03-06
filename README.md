# Knitting-Calculator
# Purpose
My sister is a knitter, and she came to me with a problem one day; when increasing or decreasing a large row, it is difficult to figure out the pattern for combining or adding stitches. Noticing that this can be simplified into an array problem, I spent the next few weeks writing this program.

# Disclaimer
I solved the problem with pen and paper and wrote code to test my ideas. Because my program wasn't planned out beforehand, it is a complete mess.
Furthermore, this is not an Object Oriented solution, so my use of classes is unnecessary and complicated. Regardless, it serves as a proof of concept and was 
great practice for conceptual problem solving.

# Explanation
For this project, one main challenge was concretely describing how elements are distributed on an array. So, I simplified an optimal array - that is, an evenly distributed array -
as a series of chunks. A chunk is made of either blank space or elements, depending on what dominates the array.
For example, if 'E' represents an element, and '-' represents an empty cell:  <br /> [--E---E--E---E--] and [EE-EEE-EE-EEE-EE] both have a chunk array of [2,3,2,3,2].

I have established two rules that result in an optimal array:
1) Chunk sizes are as similar as possible. Or, more specifically, that the sizes of each chunk can differ at most by one.
This rule is enforced by my OpSum algorithm, which finds the sizes of these chunks and can be seen in detail in here: https://github.com/mktwohy/OperandsFromSum-Calculator

2) Chunks are sorted; not only does the size of each chunk matter, but so does their order. If there is at least one unique chunk size, this rule is enforced by 
my chunkSortAlgorithm. 
  - First, the rarer chunk size is assigned to parent, and the more common size to child. For instance, [1, 1, 1, 1, 1, 1, 2, 2] 
  has two parents of size 2 and six children of size 1. 
  - Second, each parent is dealt children in a card-dealing fashion. This ensures that the number of children per parent is as similar as possible. So, the previous array 
  becomes [2, 1, 1, 1, 2, 1, 1, 1].
 This style of sorting is ideal, as the result can be broken down into repetative sub-chunks (two groups of [2,1,1,1]), which are easier to remember for a knitter. 

The sorted chunk array is then converted to an element array. So, using the chunk array from rule 2:,  <br />  7 elements on an array of size 17 = [--E-E-E-E--E-E-E-], 


package org.examples.v3.similarity;

import java.util.*;

public class Markov2
{

private List<String> axies;
private double[][] matrix;

public Markov2()
{

axies = new ArrayList<String>();
}

public void getSimilarity(List<String> s)
{
String temp = new String();
for (int i = 0; i < s.size(); i++)
{
if (temp.length() > 0)
{
temp = temp + " " + s.get(i);
}
else temp = s.get(i);

}
setupAxis(temp);
compareCharsAgainstAxis(temp);

display();

for (int i = 0; i < matrix.length; i++)
{
double line = getLineValue(i);
for (int j = 0; j < matrix[i].length; j++)
{
double t = matrix[i][j];

matrix[i][j] = t / line;
}
}

display();
}

private void compareCharsAgainstAxis(String s)
{
matrix = new double[axies.size()][axies.size()];
String comparison = "";
String test = s;

for (int h = 0; h < test.length(); h++)
{
int end = test.indexOf(" ");
if (end > 0)
{
comparison = test.substring(0,end);
}
else
{
comparison = test.substring(0,test.length());
end = test.length()-1;
}
test = test.substring(end+1,test.length());

for (int i = 1; i < comparison.length(); i++)
{
for (int j = 0; j < axies.size(); j++)
{
for (int k = 0; k < axies.size(); k++)
{
if (String.valueOf(comparison.charAt(i-1)).equalsIgnoreCase(axies.get(j)) && String.valueOf(comparison.charAt(i)).equalsIgnoreCase(axies.get(k)))
{
matrix[j][k]++;
}
}
}
}
}

}
private void setupAxis(String temp)
{
for (int j = 0; j < temp.length(); j++)
{
boolean found = false;

for (int k = 0; k < axies.size() && !found; k++)
{
if (String.valueOf(temp.charAt(j)).equalsIgnoreCase(axies.get(k)))
{
found = true;
}
}

if (!found)
{
if (temp.charAt(j) > 32)
{
axies.add(String.valueOf(temp.charAt(j)));
}
}
}
}

private double getTotalValue()
{
double sum = 0;
for (int i = 0; i < matrix.length; i++)
{
for (int j = 0; j < matrix[i].length; j++)
{
double t = matrix[i][j];
sum = sum + t;
}
}
return sum;
}

private double getLineValue(int line)
{
double sum = 0;
for (int i = 0; i < matrix[line].length; i++)
{
double t = matrix[line][i];
sum = sum + t;
}
return sum;
}

private void display()
{
System.out.println("Sum of matrix is "+getTotalValue());
System.out.println("Sum of line 1 = "+getLineValue(3));

System.out.print(" ");
for (int j = 0; j < axies.size(); j++)
{
System.out.print(axies.get(j)+" ");
}
System.out.println();
for (int i = 0; i < matrix.length; i++)
{
System.out.print(axies.get(i)+" ");
for (int j = 0; j < matrix[i].length; j++)
{
System.out.print(matrix[i][j]+" ");
}
System.out.println();
}
}

public static void main(String[] args) throws Exception {
	Markov2 m = new Markov2();
	List<String> s = new ArrayList<String>();
	s.add("test1");
	s.add("test2");
	s.add("test3");
	s.add("test4");
	s.add("abcd");
	m.getSimilarity(s);
}

}

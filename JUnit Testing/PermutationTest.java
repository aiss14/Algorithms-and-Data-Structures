import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=0;

	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	int fact(int a) {
		if (a==0) return 1;
		int fac=1;
		for(int i=1 ; i<=a;i++){
			fac *= i;
		}
		return fac;
	}

	int subfac(int n){
		double sum=0;
		for (int i=0;i<=n;i++){
			sum = sum + Math.pow(-1,i)/fact(i);
		}
		sum *= fact(n);
		return (int)sum;
	}
	void correctelements(PermutationVariation p){
		for(int c_list=0; c_list<p.allDerangements.size();c_list++){
			assertEquals(p.allDerangements.get(c_list).length,p.original.length);
			for(int c_tab: p.original){
				int freq=0;
				for(int i:p.original){
					if (c_tab==i){
						freq++;
					}
				}
				assertEquals(freq,1);
			}

		}
	}

	@Test
	void testPermutation() {
		initialize();
		assertNotNull(p1.original);
		assertNotNull(p2.original);
		assertNotNull(p1.allDerangements);
		assertNotNull(p2.allDerangements);
		assertEquals(n1,p1.original.length);
		assertEquals(n2,p2.original.length);
		assertTrue(p1.allDerangements.isEmpty());
		assertTrue(p2.allDerangements.isEmpty());
        // both loops to see if a digit comes twice , for p1 and p2;
		for(int i=0 ; i<p1.original.length;i++){
			for(int j=i+1 ; j<p1.original.length;j++){
                  assertNotEquals(p1.original[i],p1.original[j]);
			}
		}
		for(int i=0 ; i<p2.original.length;i++){
			for(int j=i+1 ; j<p2.original.length;j++){
				assertNotEquals(p2.original[i],p2.original[j]);
			}
		}

		// TODO
	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		p1.derangements();
		p2.derangements();
		assertEquals(p1.allDerangements.size(),subfac(p1.original.length));
		assertEquals(p1.allDerangements.size(),subfac(p2.original.length));
		for(int[]i:p1.allDerangements){
			for(int j=0;j<i.length;j++){
				assertNotEquals(i[j],p1.original[j]);
			}

		}
		for(int[]i:p2.allDerangements){
			for(int j=0;j<i.length;j++){
				assertNotEquals(i[j],p2.original[j]);
			}

		}
		// TODO
	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		assertTrue(p1.allDerangements.size()!=0);
		assertTrue(p2.allDerangements.size()!=0);
		assertNotEquals(p1.original.length,0);
		assertNotEquals(p1.original.length,0);
		correctelements(p1);
		correctelements(p2);
		// TODO
	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}



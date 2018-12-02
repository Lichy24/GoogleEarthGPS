package GIS;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * GISLayer is an objects implementing GIS_layer and represents a set of GIS_element objects.
 * GISLayer is an object made from a single file of CSV
 * @author Ofek Bader
 */
public class GISLayer implements GIS_layer{

	Set<GIS_element> elements;
	private MetaDataSet metadata;
	
	public GISLayer() {
		elements = new LinkedHashSet<GIS_element>();
		metadata = new MetaDataSet();
	}

	@Override
	public boolean add(GIS_element e) {
		return elements.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> c) {
		return elements.addAll(c);
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<GIS_element> iterator() {
		return elements.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return elements.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return elements.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return elements.retainAll(c);
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	@Override
	public Meta_data get_Meta_data() {
		return metadata;
	}

	/**
	 * toString create a string of all contained object in GISLayer.
	 * @return String of GIS Layer.
	 */
	@Override
	public String toString() {
		Iterator iterator = iterator();
		int count = 1;
		String str = "Layer:[ ";
		if (!iterator.hasNext())
			return str+"]\n";
		while (iterator.hasNext()){
			str += count+" "+iterator.next().toString()+" , ";
			count++;
		}
		str = str.substring(0,str.length()-2);
		str += "]\n";
		return str;
	}
	
}

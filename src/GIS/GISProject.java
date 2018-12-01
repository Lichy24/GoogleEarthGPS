package GIS;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * GISProject is an objects implementing GIS_project and represents a set of GIS_layer objects.
 * the GISProject object can be convert to KML with the Csv2Kml class.
 * @author Ofek Bader
 */
public class GISProject implements GIS_project{

	Set<GIS_layer> layers;
	private MetaDataSet metadata;
	
	public GISProject() {
		layers = new LinkedHashSet<GIS_layer>();
		metadata = new MetaDataSet();
	}
	
	@Override
	public boolean add(GIS_layer e) {
		return layers.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		return layers.addAll(c);

	}

	@Override
	public void clear() {
		layers.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		return layers.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return layers.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return layers.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		return layers.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return layers.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return layers.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return layers.retainAll(c);
	}

	@Override
	public int size() {
		return layers.size();
	}

	@Override
	public Object[] toArray() {
		return layers.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return layers.toArray(a);
	}

	@Override
	public Meta_data get_Meta_data() {
		return metadata;
	}

	/**
	 * toString create a string of all contained object in GISProject.
	 * @return String of GISProject.
	 */
	@Override
	public String toString() {
		Iterator iterator = iterator();
		String str = "Project:[ ";
		int count = 1;
		while (iterator.hasNext()){
			str += count +" "+ iterator.next().toString()+" , ";
			count++;
		}
		str = str.substring(0,str.length()-2);
		str += "]";
		return str;
	}
}

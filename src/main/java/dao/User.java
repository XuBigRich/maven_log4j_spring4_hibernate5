package dao;

import javax.persistence.*;

@Entity//声明当前类为hibernate映射到数据库中的实体类
@Table(name = "t_user")//声明在数据库中自动生成的表名为t_user
public class User {
	@Id//声明此列为主键,作为映射对象的标识符
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//重写hashcode方法提高比较效率
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	//重写equals比较对象相等
	@Override
	public boolean equals(Object obj) {
		if (this == obj)//如果地址引用相同，直接判断为相等
			return true;
		if (obj == null)//如果目标对象为null,直接判断不等
			return false;
		if (getClass() != obj.getClass())//两者类不一致
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))//判断两者id是否都存在且相等
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))//判断两者名字是否都存在且相等
			return false;
		return true;
	}
}
package Modelo;

import Database.Conexion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class ProductoDAO extends Conexion {

    private Connection con;
    private Conexion cn = new Conexion();
    private PreparedStatement ps;
    private ResultSet rs;
    private int r = 0;

    public List buscar(String nombre) {
        List list = new ArrayList();
        String sql = "select * from producto where Nombres like '%" + nombre + "%'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setImagen(rs.getString(3));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                list.add(p);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Producto listarId(int id) {
        Producto p = new Producto();
        String sql = "select * from producto where IdProducto=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setImagen(rs.getString(3));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
            }
        } catch (Exception e) {
        }
        return p;
    }

    public List listar() {
        List lista = new ArrayList();
        String sql = "select * from producto";
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setImagen(rs.getString(3));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                lista.add(p);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public void listarImg(int id, HttpServletResponse response) {
        String sql = "select * from producto where IdProducto=" + id;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*");
        try {
            outputStream = response.getOutputStream();
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                inputStream = rs.getBinaryStream("Foto");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        } catch (Exception e) {
        }
    }

    public int AgregarNuevoProducto(Producto p) {
        String sql = "insert into producto(Nombres,Foto,Descripcion,Precio,Stock)values(?,?,?,?,?)";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getImagen());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public int eliminarProducto(int idProducto) {
        String sql = "DELETE FROM producto WHERE idProducto = ?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas; // Devuelve el número de filas afectadas por la operación de eliminación
        } catch (Exception e) {
            e.printStackTrace(); // Manejar las excepciones adecuadamente en tu aplicación
            return -1; // Retorna un valor negativo para indicar que ocurrió un error
        }
    }

}

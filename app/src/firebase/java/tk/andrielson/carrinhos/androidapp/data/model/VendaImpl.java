package tk.andrielson.carrinhos.androidapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by anfesilva on 12/03/2018.
 */

public class VendaImpl
        extends AbsEntidadePadrao
        implements Venda<ItemVendaImpl, VendedorImpl> {

    public static final String COLECAO = "vendas";
    public static final String CODIGO = "codigo";
    public static final String COMISSAO = "comissao";
    public static final String DATA = "data";
    public static final String TOTAL = "total";
    public static final String VENDEDOR = "vendedor";
    public static final String VENDEDOR_NOME = "vendedor_nome";
    public static final String STATUS = "status";
    public static final String ITENS = "itens";

    private Long codigo;
    private Integer comissao;
    private Date data;
    private Long total;
    private VendedorImpl vendedor;
    private String vendedorNome;
    private String status;
    private List<ItemVendaImpl> itens;

    public VendaImpl() {

    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getComissao() {
        return comissao;
    }

    public void setComissao(Integer comissao) {
        this.comissao = comissao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public VendedorImpl getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorImpl vendedor) {
        this.vendedor = vendedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public void setVendedorNome(String vendedorNome) {
        this.vendedorNome = vendedorNome;
    }

    @Override
    public List<ItemVendaImpl> getItens() {
        return itens;
    }

    @Override
    public void setItens(List<ItemVendaImpl> itens) {
        this.itens = itens;
    }

    private VendaImpl(Parcel in) {
        codigo = in.readByte() == 0x00 ? null : in.readLong();
        comissao = in.readByte() == 0x00 ? null : in.readInt();
        long tmpData = in.readLong();
        data = tmpData != -1 ? new Date(tmpData) : null;
        total = in.readByte() == 0x00 ? null : in.readLong();
        vendedor = (VendedorImpl) in.readValue(VendedorImpl.class.getClassLoader());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VendaImpl venda = (VendaImpl) o;

        if (codigo != null ? !codigo.equals(venda.codigo) : venda.codigo != null) return false;
        if (comissao != null ? !comissao.equals(venda.comissao) : venda.comissao != null)
            return false;
        if (data != null ? !data.equals(venda.data) : venda.data != null) return false;
        if (total != null ? !total.equals(venda.total) : venda.total != null) return false;
        if (vendedor != null ? !vendedor.equals(venda.vendedor) : venda.vendedor != null)
            return false;
        return itens != null ? itens.equals(venda.itens) : venda.itens == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (comissao != null ? comissao.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (vendedor != null ? vendedor.hashCode() : 0);
        result = 31 * result + (itens != null ? itens.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (codigo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(codigo);
        }
        if (comissao == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(comissao);
        }
        dest.writeLong(data != null ? data.getTime() : -1L);
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(total);
        }
        dest.writeValue(vendedor);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VendaImpl> CREATOR = new Parcelable.Creator<VendaImpl>() {
        @Override
        public VendaImpl createFromParcel(Parcel in) {
            return new VendaImpl(in);
        }

        @Override
        public VendaImpl[] newArray(int size) {
            return new VendaImpl[size];
        }
    };
}

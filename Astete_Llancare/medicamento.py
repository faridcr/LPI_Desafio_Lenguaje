class Medicamento:
    def __init__(self, nombre, stock):
        self.nombre = nombre
        self.stock = stock

    def descontar_stock(self):
        if self.stock > 0:
            self.stock -= 1
        else:
            print("No hay stock disponible de", self.nombre)

    def mostrar_medicamento(self):
        print(self.nombre, "- Stock disponible:", self.stock)
